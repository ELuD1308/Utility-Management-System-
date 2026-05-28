package com.utilityinternational.utility_backend.controller;

import com.utilityinternational.utility_backend.dto.request.DisputeRequest;
import com.utilityinternational.utility_backend.dto.request.DisputeStatusUpdateRequest;
import com.utilityinternational.utility_backend.dto.response.DisputeResponse;
import com.utilityinternational.utility_backend.service.DisputeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/disputes")
@RequiredArgsConstructor
public class DisputeController {

    private final DisputeService disputeService;

    // POST /api/disputes/customer/{customerId}
    @PostMapping("/customer/{customerId}")
    public ResponseEntity<DisputeResponse> openDispute(
            @PathVariable Long customerId,
            @Valid @RequestBody DisputeRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(disputeService.openDispute(customerId, request));
    }

    // GET /api/disputes/customer/{customerId}
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<DisputeResponse>> getMyDisputes(
            @PathVariable Long customerId) {

        return ResponseEntity.ok(disputeService.getDisputesByCustomer(customerId));
    }

    // GET /api/disputes/customer/{customerId}/{disputeId}
    @GetMapping("/customer/{customerId}/{disputeId}")
    public ResponseEntity<DisputeResponse> trackDispute(
            @PathVariable Long customerId,
            @PathVariable Long disputeId) {

        return ResponseEntity.ok(disputeService.getDisputeById(customerId, disputeId));
    }

    // GET /api/disputes/agent/all
    @GetMapping("/agent/all")
    public ResponseEntity<List<DisputeResponse>> getAllDisputes() {
        return ResponseEntity.ok(disputeService.getAllDisputes());
    }

    // PATCH /api/disputes/agent/{disputeId}/status
    @PatchMapping("/agent/{disputeId}/status")
    public ResponseEntity<DisputeResponse> updateStatus(
            @PathVariable Long disputeId,
            @Valid @RequestBody DisputeStatusUpdateRequest request) {

        return ResponseEntity.ok(disputeService.updateDisputeStatus(disputeId, request));
    }
}
