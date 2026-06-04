package com.utilityinternational.utility_backend.service;

import com.utilityinternational.utility_backend.dto.request.PaymentRequest;
import com.utilityinternational.utility_backend.dto.response.PaymentResponse;
import com.utilityinternational.utility_backend.entity.Payment;

import java.util.List;

public interface PaymentService {

    PaymentResponse createStandingOrder(PaymentRequest request);

    PaymentResponse createDirectDebit(PaymentRequest request);

    List<PaymentResponse> getPaymentHistory();

    Payment createPayment(Payment payment);

    Payment getPaymentById(Long id);

    List<Payment> getAllPayments();

    Payment updatePayment(Long id, Payment payment);

    void deletePayment(Long id);
}