package com.utilityinternational.utility_backend.service;

import com.utilityinternational.utility_backend.dto.response.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerService {

    CustomerProfileResponse getMyProfile();

    Page<BillResponse> getMyBills(Pageable pageable);

    BillResponse getBillById(Long billId);

    Page<UtilityUsageResponse> getMyUsageHistory(Pageable pageable);

    Page<TariffHistoryResponse> getMyTariffHistory(Pageable pageable);

    long getUnreadNotificationCount();
}
