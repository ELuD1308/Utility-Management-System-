package com.utilityinternational.utility_backend.dto.response;

import com.utilityinternational.utility_backend.enums.UtilityType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TariffResponse {

    private Long tariffId;
    private String tariffName;
    private UtilityType utilityType;
    private Double ratePerUnit;
}
