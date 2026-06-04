package com.utilityinternational.utility_backend.dto.response;

import com.utilityinternational.utility_backend.enums.UtilityType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UtilityUsageResponse {

    private Long id;
    private LocalDate readingDate;
    private Integer unitsConsumed;
    private Integer meterReadingStart;
    private Integer meterReadingEnd;
    private UtilityType utilityType;
    private LocalDate recordedAt;
}
