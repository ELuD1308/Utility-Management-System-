package com.utilityinternational.utility_backend.controller;

import com.utilityinternational.utility_backend.dto.request.PaymentRequest;
import com.utilityinternational.utility_backend.dto.response.PaymentResponse;
import com.utilityinternational.utility_backend.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/standing-order")
    public ResponseEntity<PaymentResponse> createStandingOrder(
            @Valid @RequestBody PaymentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(paymentService.createStandingOrder(request));
    }

    @PostMapping("/direct-debit")
    public ResponseEntity<PaymentResponse> createDirectDebit(
            @Valid @RequestBody PaymentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(paymentService.createDirectDebit(request));
    }

    @GetMapping("/history")
    public ResponseEntity<List<PaymentResponse>> getPaymentHistory() {
        return ResponseEntity.ok(paymentService.getPaymentHistory());
    }

}