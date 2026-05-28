package com.utilityinternational.utility_backend.service;

import com.utilityinternational.utility_backend.dto.response.BillResponse;
import com.utilityinternational.utility_backend.dto.response.CustomerProfileResponse;
import com.utilityinternational.utility_backend.dto.response.NotificationResponse;
import com.utilityinternational.utility_backend.dto.response.PaymentResponse;

import java.util.List;

public interface CustomerService {

    CustomerProfileResponse getProfile(Long customerId);

    List<BillResponse> getMyBills(Long customerId);

    BillResponse getBillById(Long customerId, Long billId);

    List<PaymentResponse> getPaymentHistory(Long customerId);

    List<NotificationResponse> getNotifications(Long customerId);
}
