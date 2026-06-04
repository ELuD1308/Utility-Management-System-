package com.utilityinternational.utility_backend.controller;

import com.utilityinternational.utility_backend.entity.Bill;
import com.utilityinternational.utility_backend.service.BillService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/bills")
@RequiredArgsConstructor
public class BillController {

    private final BillService billService;

    @GetMapping("/customer/{customerId}")
    public List<Bill> getCustomerBills(
            @PathVariable Long customerId) {

        return billService.getBillsForCustomer(customerId);
    }

    @GetMapping("/{billId}")
    public Bill getBill(
            @PathVariable Long billId) {

        return billService.getBillById(billId);
    }

    @GetMapping("/{billId}/amount")
    public BigDecimal getBillAmount(
            @PathVariable Long billId) {

        return billService.calculateBill(billId);
    }

    @PutMapping("/{billId}/pay")
    public Bill markAsPaid(
            @PathVariable Long billId) {

        return billService.markBillAsPaid(billId);
    }
}