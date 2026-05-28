package com.utilityinternational.utility_backend.service;

import com.utilityinternational.utility_backend.dto.response.BillResponse;
import com.utilityinternational.utility_backend.dto.response.CustomerProfileResponse;
import com.utilityinternational.utility_backend.dto.response.NotificationResponse;
import com.utilityinternational.utility_backend.dto.response.PaymentResponse;
import com.utilityinternational.utility_backend.entity.Bill;
import com.utilityinternational.utility_backend.entity.Customer;
import com.utilityinternational.utility_backend.entity.Payment;
import com.utilityinternational.utility_backend.enums.BillStatus;
import com.utilityinternational.utility_backend.exception.ResourceNotFoundException;
import com.utilityinternational.utility_backend.repository.BillRepository;
import com.utilityinternational.utility_backend.repository.CustomerRepository;
import com.utilityinternational.utility_backend.repository.NotificationRepository;
import com.utilityinternational.utility_backend.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final BillRepository billRepository;
    private final PaymentRepository paymentRepository;
    private final NotificationRepository notificationRepository;

    // ── Profile ────────────────────────────────────────────────────────────────
    @Override
    @Transactional(readOnly = true)
    public CustomerProfileResponse getProfile(Long customerId) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Customer", "id", customerId));

        return CustomerProfileResponse.builder()
                .customerId(customer.getId())
                .fullName(customer.getFullName())
                .email(customer.getEmail())
                .memberSince(customer.getCreatedAt())
                .build();
    }

    // ── Bills ──────────────────────────────────────────────────────────────────
    @Override
    @Transactional(readOnly = true)
    public List<BillResponse> getMyBills(Long customerId) {

        customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Customer", "id", customerId));

        return billRepository.findByCustomerId(customerId)
                .stream()
                .map(this::mapToBillResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public BillResponse getBillById(Long customerId, Long billId) {

        return billRepository.findByIdAndCustomerId(billId, customerId)
                .map(this::mapToBillResponse)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Bill", "id", billId));
    }

    // ── Payment history ────────────────────────────────────────────────────────
    @Override
    @Transactional(readOnly = true)
    public List<PaymentResponse> getPaymentHistory(Long customerId) {

        customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Customer", "id", customerId));

        return paymentRepository.findByBillCustomerId(customerId)
                .stream()
                .map(this::mapToPaymentResponse)
                .collect(Collectors.toList());
    }

    // ── Notifications ──────────────────────────────────────────────────────────
    @Override
    @Transactional(readOnly = true)
    public List<NotificationResponse> getNotifications(Long customerId) {

        customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Customer", "id", customerId));

        return notificationRepository
                .findByCustomerIdOrderByCreatedAtDesc(customerId)
                .stream()
                .map(n -> NotificationResponse.builder()
                        .notificationId(n.getId())
                        .message(n.getMessage())
                        .notificationType(n.getNotificationType())
                        .sentAt(n.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }

    // ── Entity → DTO mappings ──────────────────────────────────────────────────
    private BillResponse mapToBillResponse(Bill bill) {

        boolean overdue = bill.getDueDate() != null
                && bill.getDueDate().isBefore(LocalDate.now())
                && bill.getStatus() != BillStatus.PAID;

        return BillResponse.builder()
                .billId(bill.getId())
                .amount(bill.getAmount())
                .dueDate(bill.getDueDate())
                .status(bill.getStatus())
                .overdue(overdue)
                .createdAt(bill.getCreatedAt())
                .build();
    }

    private PaymentResponse mapToPaymentResponse(Payment payment) {
        return PaymentResponse.builder()
                .paymentId(payment.getId())
                .billId(payment.getBill().getId())
                .amount(payment.getAmount())
                .status(payment.getStatus())
                .paidAt(payment.getCreatedAt())
                .build();
    }
}
