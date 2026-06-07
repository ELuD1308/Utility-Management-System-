package com.utilityinternational.utility_backend.controller;

import com.utilityinternational.utility_backend.dto.response.BillResponse;
import com.utilityinternational.utility_backend.dto.response.CustomerProfileResponse;
import com.utilityinternational.utility_backend.dto.response.NotificationResponse;
import com.utilityinternational.utility_backend.dto.response.PaymentResponse;
import com.utilityinternational.utility_backend.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    // GET /api/customers/{customerId}/profile
    @GetMapping("/{customerId}/profile")
    public ResponseEntity<CustomerProfileResponse> getProfile(
            @PathVariable Long customerId) {

        return ResponseEntity.ok(customerService.getProfile(customerId));
    }

    // GET /api/customers/{customerId}/bills
    @GetMapping("/{customerId}/bills")
    public ResponseEntity<List<BillResponse>> getBills(
            @PathVariable Long customerId) {

        return ResponseEntity.ok(customerService.getMyBills(customerId));
    }

    // GET /api/customers/{customerId}/bills/{billId}
    @GetMapping("/{customerId}/bills/{billId}")
    public ResponseEntity<BillResponse> getBillById(
            @PathVariable Long customerId,
            @PathVariable Long billId) {

        return ResponseEntity.ok(customerService.getBillById(customerId, billId));
    }

    // GET /api/customers/{customerId}/payments
    @GetMapping("/{customerId}/payments")
    public ResponseEntity<List<PaymentResponse>> getPaymentHistory(
            @PathVariable Long customerId) {

        return ResponseEntity.ok(customerService.getPaymentHistory(customerId));
    }

    // GET /api/customers/{customerId}/notifications
    @GetMapping("/{customerId}/notifications")
    public ResponseEntity<List<NotificationResponse>> getNotifications(
            @PathVariable Long customerId) {

        return ResponseEntity.ok(customerService.getNotifications(customerId));
    }
}
