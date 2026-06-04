package com.utilityinternational.utility_backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TariffHistoryResponse {

    private Long id;
    private String previousPlanCode;
    private String previousPlanName;
    private String newPlanCode;
    private String newPlanName;
    private String changedBy;
    private String changeReason;
    private LocalDateTime changedAt;
}
