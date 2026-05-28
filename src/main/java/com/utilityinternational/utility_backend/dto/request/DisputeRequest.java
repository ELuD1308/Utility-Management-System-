package com.utilityinternational.utility_backend.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class DisputeRequest {

    @NotNull(message = "Bill ID is required")
    private Long billId;

    @Size(min = 20, max = 1000,
          message = "Dispute reason must be between 20 and 1000 characters")
    private String disputeReason;
}
