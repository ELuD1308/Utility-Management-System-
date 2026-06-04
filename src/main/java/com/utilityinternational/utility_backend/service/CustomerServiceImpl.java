package com.utilityinternational.utility_backend.service;

import com.utilityinternational.utility_backend.dto.response.*;
import com.utilityinternational.utility_backend.entity.*;
import com.utilityinternational.utility_backend.exception.ResourceNotFoundException;
import com.utilityinternational.utility_backend.repository.*;
import com.utilityinternational.utility_backend.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
// import java.util.List;
// import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final BillRepository billRepository;
    private final UtilityUsageRepository utilityUsageRepository;
    private final TariffHistoryRepository tariffHistoryRepository;
    private final NotificationRepository notificationRepository;

    @Override
    @Transactional(readOnly = true)
    public CustomerProfileResponse getMyProfile() {
        Customer customer = resolveCurrentCustomer();
        return mapToProfileResponse(customer);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BillResponse> getMyBills(Pageable pageable) {
        Customer customer = resolveCurrentCustomer();
        return billRepository.findByCustomerIdOrderByCreatedAtDesc(customer.getId(), pageable)
            .map(this::mapToBillResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public BillResponse getBillById(Long billId) {
        Customer customer = resolveCurrentCustomer();
        Bill bill = billRepository.findByCustomerIdAndId(customer.getId(), billId)
            .orElseThrow(() -> new ResourceNotFoundException("Bill", "id", billId));
        return mapToBillResponse(bill);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UtilityUsageResponse> getMyUsageHistory(Pageable pageable) {
        Customer customer = resolveCurrentCustomer();
        return utilityUsageRepository
            .findByCustomerIdOrderByReadingDateDesc(customer.getId(), pageable)
            .map(this::mapToUsageResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TariffHistoryResponse> getMyTariffHistory(Pageable pageable) {
        Customer customer = resolveCurrentCustomer();
        return tariffHistoryRepository
            .findByCustomerIdOrderByChangedAtDesc(customer.getId(), pageable)
            .map(this::mapToTariffHistoryResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public long getUnreadNotificationCount() {
        Customer customer = resolveCurrentCustomer();
        return notificationRepository.findById(customer.getId()).stream()
            .filter(notification -> !notification.isRead())
            .count();
    }

    private Customer resolveCurrentCustomer() {
        String fullName = SecurityUtils.getCurrentFullName();
        return customerRepository.findByFullNameWithTariff(fullName)
            .orElseThrow(() -> new ResourceNotFoundException("Customer", "full name", fullName));
    }

    private CustomerProfileResponse mapToProfileResponse(Customer c) {
        Customer customer = c.getCustomer();
        return CustomerProfileResponse.builder()
            .customerId(c.getId())
            .userId(c.getId())
            .username(c.getUsername())
            .email(c.getEmail())
            .firstName(c.getFirstName())
            .lastName(c.getLastName())
            .phoneNumber(c.getPhoneNumber())
            .accountNumber(c.getAccountNumber())
            .address(c.getAddress())
            .city(c.getCity())
            .state(c.getState())
            .postalCode(c.getPostalCode())
            .currentTariff(c.getCurrentTariff() != null ? mapToTariffResponse(c.getCurrentTariff()) : null)
            .createdAt(c.getCreatedAt())
            .updatedAt(c.getUpdatedAt())
            .build();
    }

    private BillResponse mapToBillResponse(Bill b) {
        BigDecimal balance = b.getAmountDue().subtract(b.getAmountPaid());
        return BillResponse.builder()
            .id(b.getId())
            .billNumber(b.getBillNumber())
            .billingPeriodStart(b.getBillingPeriodStart())
            .billingPeriodEnd(b.getBillingPeriodEnd())
            .dueDate(b.getDueDate())
            .totalUnitsConsumed(b.getTotalUnitsConsumed())
            .amountDue(b.getAmountDue())
            .amountPaid(b.getAmountPaid())
            .balance(balance.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : balance)
            .status(b.getStatus())
            .paymentDate(b.getPaymentDate())
            .createdAt(b.getCreatedAt())
            .build();
    }

    private UtilityUsageResponse mapToUsageResponse(UtilityUsage u) {
        return UtilityUsageResponse.builder()
            .id(u.getId())
            .readingDate(u.getReadingDate())
            .unitsConsumed(u.getUnitsConsumed())
            .meterReadingStart(u.getMeterReadingStart())
            .meterReadingEnd(u.getMeterReadingEnd())
            .utilityType(u.getUtilityType())
            .recordedAt(u.getRecordedAt())
            .build();
    }

    private TariffHistoryResponse mapToTariffHistoryResponse(TariffHistory th) {
        return TariffHistoryResponse.builder()
            .id(th.getId())
            .previousPlanCode(th.getPreviousTariff() != null ? th.getPreviousTariff().getPlanCode() : null)
            .previousPlanName(th.getPreviousTariff() != null ? th.getPreviousTariff().getPlanName() : null)
            .newPlanCode(th.getNewTariff().getPlanCode())
            .newPlanName(th.getNewTariff().getPlanName())
            .changedBy(th.getChangedBy())
            .changeReason(th.getChangeReason())
            .changedAt(th.getChangedAt())
            .build();
    }

    private TariffResponse mapToTariffResponse(Tariff t) {
        return TariffResponse.builder()
            .id(t.getId())
            .planCode(t.getPlanCode())
            .planName(t.getPlanName())
            .description(t.getDescription())
            .ratePerUnit(t.getRatePerUnit())
            .monthlyFixedCharge(t.getMonthlyFixedCharge())
            .includedUnits(t.getIncludedUnits())
            .active(t.isActive())
            .build();
    }
}
