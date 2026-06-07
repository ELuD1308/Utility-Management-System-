package com.utilityinternational.utility_backend.service;

import com.utilityinternational.utility_backend.dto.request.TariffChangeRequest;
import com.utilityinternational.utility_backend.dto.response.TariffResponse;
import com.utilityinternational.utility_backend.enums.UtilityType;

import java.util.List;

public interface TariffService {

    List<TariffResponse> getAllTariffs();

    List<TariffResponse> getTariffsByUtilityType(UtilityType utilityType);

    TariffResponse changeCustomerTariff(Long customerId, TariffChangeRequest request);
}
