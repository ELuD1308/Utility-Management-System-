package com.utilityinternational.utility_backend.dto.response;

import com.utilityinternational.utility_backend.enums.DisputeStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class DisputeResponse {

    private Long disputeId;
    private Long billId;
    private String billAmount;
    private String disputeReason;
    private DisputeStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
