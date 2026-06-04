package com.utilityinternational.utility_backend.service;

import com.utilityinternational.utility_backend.entity.Bill;

import java.math.BigDecimal;
import java.util.List;

public interface BillService {

    List<Bill> getBillsForCustomer(Long customerId);

    Bill getBillById(Long billId);

    BigDecimal calculateBill(Long billId);

    Bill markBillAsPaid(Long billId);
}