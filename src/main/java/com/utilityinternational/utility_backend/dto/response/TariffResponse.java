package com.utilityinternational.utility_backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TariffResponse {

    private Long id;
    private String planCode;
    private String planName;
    private String description;
    private Double ratePerUnit;
    private Double monthlyFixedCharge;
    private Integer includedUnits;
    private boolean active;
}
