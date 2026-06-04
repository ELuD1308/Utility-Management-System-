package com.utilityinternational.utility_backend.service;

import com.utilityinternational.utility_backend.dto.request.PaymentRequest;
import com.utilityinternational.utility_backend.dto.response.PaymentResponse;
import com.utilityinternational.utility_backend.entity.Bill;
import com.utilityinternational.utility_backend.entity.Payment;
import com.utilityinternational.utility_backend.enums.BillStatus;
import com.utilityinternational.utility_backend.enums.PaymentMethod;
import com.utilityinternational.utility_backend.enums.PaymentStatus;
import com.utilityinternational.utility_backend.exception.ResourceNotFoundException;
import com.utilityinternational.utility_backend.repository.BillRepository;
import com.utilityinternational.utility_backend.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final BillRepository billRepository;

    @Override
    @Transactional
    public PaymentResponse createStandingOrder(PaymentRequest request) {
        return createPayment(request, PaymentMethod.STANDING_ORDER);
    }

    @Override
    @Transactional
    public PaymentResponse createDirectDebit(PaymentRequest request) {
        return createPayment(request, PaymentMethod.DIRECT_DEBIT);
    }

    @Override
    public List<PaymentResponse> getPaymentHistory() {
        return paymentRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"))
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Payment createPayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    @Override
    public Payment getPaymentById(Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    @Override
    public Payment updatePayment(Long id, Payment payment) {

        Payment existing = getPaymentById(id);

        existing.setAmount(payment.getAmount());
        existing.setStatus(payment.getStatus());
        existing.setBill(payment.getBill());

        return paymentRepository.save(existing);
    }

    @Override
    public void deletePayment(Long id) {
        paymentRepository.deleteById(id);
    }

    private PaymentResponse createPayment(PaymentRequest request, PaymentMethod paymentMethod) {
        Objects.requireNonNull(request, "payment request must not be null");

        Bill bill = billRepository.findById(request.getBillId())
                .orElseThrow(() -> new ResourceNotFoundException("Bill", "id", request.getBillId()));

        Payment payment = Payment.builder()
                .amount(bill.getAmount())
                .status(PaymentStatus.SUCCESS)
                .paymentMethod(paymentMethod)
                .bill(bill)
                .build();

        Payment savedPayment = paymentRepository.save(payment);

        bill.setStatus(BillStatus.PAID);
        billRepository.save(bill);

        return toResponse(savedPayment);
    }

    private PaymentResponse toResponse(Payment payment) {
        return PaymentResponse.builder()
                .id(payment.getId())
                .billId(payment.getBill() != null ? payment.getBill().getId() : null)
                .amount(payment.getAmount() != null ? payment.getAmount().doubleValue() : null)
                .status(payment.getStatus())
                .paymentMethod(payment.getPaymentMethod())
                .createdAt(payment.getCreatedAt())
                .build();
    }
}