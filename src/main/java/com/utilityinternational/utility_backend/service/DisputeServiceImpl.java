package com.utilityinternational.utility_backend.service;

import com.utilityinternational.utility_backend.dto.request.DisputeRequest;
import com.utilityinternational.utility_backend.dto.request.DisputeStatusUpdateRequest;
import com.utilityinternational.utility_backend.dto.response.DisputeResponse;
import com.utilityinternational.utility_backend.entity.Bill;
import com.utilityinternational.utility_backend.entity.Customer;
import com.utilityinternational.utility_backend.entity.Dispute;
import com.utilityinternational.utility_backend.enums.BillStatus;
import com.utilityinternational.utility_backend.enums.DisputeStatus;
import com.utilityinternational.utility_backend.exception.DisputeAlreadyExistsException;
import com.utilityinternational.utility_backend.exception.InvalidDisputeTransitionException;
import com.utilityinternational.utility_backend.exception.ResourceNotFoundException;
import com.utilityinternational.utility_backend.repository.BillRepository;
import com.utilityinternational.utility_backend.repository.CustomerRepository;
import com.utilityinternational.utility_backend.repository.DisputeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DisputeServiceImpl implements DisputeService {

    private final DisputeRepository disputeRepository;
    private final BillRepository billRepository;
    private final CustomerRepository customerRepository;
    private final NotificationService notificationService;

    private static final Set<DisputeStatus> TERMINAL_STATES =
            EnumSet.of(DisputeStatus.RESOLVED, DisputeStatus.REJECTED);

    // ── Open a new dispute ────────────────────────────────────────────────────
    @Override
    @Transactional
    public DisputeResponse openDispute(Long customerId, DisputeRequest request) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Customer", "id", customerId));

        Bill bill = billRepository.findByIdAndCustomerId(request.getBillId(), customerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Bill", "id", request.getBillId()));

        if (bill.getStatus() == BillStatus.PAID) {
            throw new IllegalStateException(
                    "Cannot dispute a bill that has already been paid. Bill ID: " + bill.getId());
        }

        if (disputeRepository.existsByBillId(bill.getId())) {
            throw new DisputeAlreadyExistsException(bill.getId());
        }

        Dispute dispute = Dispute.builder()
                .bill(bill)
                .disputeReason(request.getDisputeReason())
                .status(DisputeStatus.OPEN)
                .build();

        disputeRepository.save(dispute);

        bill.setStatus(BillStatus.DISPUTED);
        billRepository.save(bill);

        log.info("Dispute opened by customer {} for bill {}", customerId, bill.getId());

        notificationService.sendNotification(
                customer,
                "Your dispute for bill #" + bill.getId() +
                " has been received and is currently OPEN. We will review it shortly.",
                "DISPUTE_OPENED"
        );

        return mapToResponse(dispute);
    }

    // ── Customer views all their disputes ─────────────────────────────────────
    @Override
    @Transactional(readOnly = true)
    public List<DisputeResponse> getDisputesByCustomer(Long customerId) {

        customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Customer", "id", customerId));

        return disputeRepository.findByBillCustomerId(customerId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // ── Customer tracks one specific dispute ──────────────────────────────────
    @Override
    @Transactional(readOnly = true)
    public DisputeResponse getDisputeById(Long customerId, Long disputeId) {

        Dispute dispute = disputeRepository.findById(disputeId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Dispute", "id", disputeId));

        if (!dispute.getBill().getCustomer().getId().equals(customerId)) {
            throw new ResourceNotFoundException("Dispute", "id", disputeId);
        }

        return mapToResponse(dispute);
    }

    // ── Agent updates dispute status ──────────────────────────────────────────
    @Override
    @Transactional
    public DisputeResponse updateDisputeStatus(Long disputeId,
                                               DisputeStatusUpdateRequest request) {

        Dispute dispute = disputeRepository.findById(disputeId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Dispute", "id", disputeId));

        DisputeStatus currentStatus = dispute.getStatus();
        DisputeStatus newStatus = request.getNewStatus();

        validateTransition(currentStatus, newStatus);

        dispute.setStatus(newStatus);
        disputeRepository.save(dispute);

        log.info("Dispute {} updated: {} -> {}", disputeId, currentStatus, newStatus);

        Customer customer = dispute.getBill().getCustomer();

        if (newStatus == DisputeStatus.RESOLVED) {
            dispute.getBill().setStatus(BillStatus.PENDING);
            billRepository.save(dispute.getBill());

            notificationService.sendNotification(
                    customer,
                    "Your dispute #" + disputeId + " has been RESOLVED." +
                    (request.getAgentNotes() != null
                            ? " Notes: " + request.getAgentNotes() : ""),
                    "DISPUTE_RESOLVED"
            );

        } else if (newStatus == DisputeStatus.REJECTED) {
            dispute.getBill().setStatus(BillStatus.PENDING);
            billRepository.save(dispute.getBill());

            notificationService.sendNotification(
                    customer,
                    "Your dispute #" + disputeId + " has been REJECTED." +
                    (request.getAgentNotes() != null
                            ? " Reason: " + request.getAgentNotes() : ""),
                    "DISPUTE_REJECTED"
            );

        } else if (newStatus == DisputeStatus.UNDER_REVIEW) {
            notificationService.sendNotification(
                    customer,
                    "Your dispute #" + disputeId + " is now UNDER REVIEW by our team.",
                    "DISPUTE_UNDER_REVIEW"
            );
        }

        return mapToResponse(dispute);
    }

    // ── Agent views all disputes ───────────────────────────────────────────────
    @Override
    @Transactional(readOnly = true)
    public List<DisputeResponse> getAllDisputes() {
        return disputeRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // ── State machine validation ───────────────────────────────────────────────
    private void validateTransition(DisputeStatus current, DisputeStatus next) {

        if (TERMINAL_STATES.contains(current)) {
            throw new InvalidDisputeTransitionException(current, next);
        }

        if (current == DisputeStatus.OPEN
                && next != DisputeStatus.UNDER_REVIEW
                && next != DisputeStatus.REJECTED) {
            throw new InvalidDisputeTransitionException(current, next);
        }

        if (current == DisputeStatus.UNDER_REVIEW
                && next != DisputeStatus.RESOLVED
                && next != DisputeStatus.REJECTED) {
            throw new InvalidDisputeTransitionException(current, next);
        }
    }

    // ── Entity → DTO ──────────────────────────────────────────────────────────
    private DisputeResponse mapToResponse(Dispute dispute) {
        return DisputeResponse.builder()
                .disputeId(dispute.getId())
                .billId(dispute.getBill().getId())
                .billAmount(dispute.getBill().getAmount().toPlainString())
                .disputeReason(dispute.getDisputeReason())
                .status(dispute.getStatus())
                .createdAt(dispute.getCreatedAt())
                .updatedAt(dispute.getUpdatedAt())
                .build();
    }
}
