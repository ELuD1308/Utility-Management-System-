package com.utilityinternational.utility_backend.controller;

import com.utilityinternational.utility_backend.dto.request.TariffChangeRequest;
import com.utilityinternational.utility_backend.dto.response.TariffResponse;
import com.utilityinternational.utility_backend.enums.UtilityType;
import com.utilityinternational.utility_backend.service.TariffService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tariffs")
@RequiredArgsConstructor
public class TariffController {

    private final TariffService tariffService;

    // GET /api/tariffs
    @GetMapping
    public ResponseEntity<List<TariffResponse>> getAllTariffs() {
        return ResponseEntity.ok(tariffService.getAllTariffs());
    }

    // GET /api/tariffs/by-type?utilityType=ELECTRICITY
    @GetMapping("/by-type")
    public ResponseEntity<List<TariffResponse>> getByUtilityType(
            @RequestParam UtilityType utilityType) {

        return ResponseEntity.ok(tariffService.getTariffsByUtilityType(utilityType));
    }

    // PUT /api/tariffs/customer/{customerId}/change
    @PutMapping("/customer/{customerId}/change")
    public ResponseEntity<TariffResponse> changeTariff(
            @PathVariable Long customerId,
            @Valid @RequestBody TariffChangeRequest request) {

        return ResponseEntity.ok(tariffService.changeCustomerTariff(customerId, request));
    }
}
