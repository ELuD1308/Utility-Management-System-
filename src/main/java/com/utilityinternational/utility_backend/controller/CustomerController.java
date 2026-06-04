package com.utilityinternational.utility_backend.controller;

import com.utilityinternational.utility_backend.dto.response.*;
import com.utilityinternational.utility_backend.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/customer")
@PreAuthorize("hasRole('CUSTOMER')")
@RequiredArgsConstructor
@Tag(name = "Customer Operations", description = "Customer self-service operations")
@SecurityRequirement(name = "bearerAuth")
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/profile")
    @Operation(summary = "Retrieve my profile")
    public ResponseEntity<CustomerProfileResponse> getProfile() {
        return ResponseEntity.ok(customerService.getMyProfile());
    }

    @GetMapping("/bills")
    @Operation(summary = "List my bills (paginated)")
    public ResponseEntity<Page<BillResponse>> getBills(
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable) {
        return ResponseEntity.ok(customerService.getMyBills(pageable));
    }

    @GetMapping("/bills/{billId}")
    @Operation(summary = "Get a specific bill by ID")
    public ResponseEntity<BillResponse> getBill(@PathVariable Long billId) {
        return ResponseEntity.ok(customerService.getBillById(billId));
    }

    @GetMapping("/usage")
    @Operation(summary = "View utility usage history")
    public ResponseEntity<Page<UtilityUsageResponse>> getUsageHistory(
            @PageableDefault(size = 10, sort = "readingDate", direction = Sort.Direction.DESC)
            Pageable pageable) {
        return ResponseEntity.ok(customerService.getMyUsageHistory(pageable));
    }

    @GetMapping("/tariff-history")
    @Operation(summary = "View my tariff plan change history")
    public ResponseEntity<Page<TariffHistoryResponse>> getTariffHistory(
            @PageableDefault(size = 10, sort = "changedAt", direction = Sort.Direction.DESC)
            Pageable pageable) {
        return ResponseEntity.ok(customerService.getMyTariffHistory(pageable));
    }

    @GetMapping("/notifications/unread-count")
    @Operation(summary = "Get count of unread notifications")
    public ResponseEntity<Map<String, Long>> getUnreadCount() {
        return ResponseEntity.ok(Map.of("unreadCount", customerService.getUnreadNotificationCount()));
    }
}
