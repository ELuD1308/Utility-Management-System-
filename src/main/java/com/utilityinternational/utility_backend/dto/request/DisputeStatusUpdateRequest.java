package com.utilityinternational.utility_backend.dto.request;

import com.utilityinternational.utility_backend.enums.DisputeStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class DisputeStatusUpdateRequest {

    @NotNull(message = "New status is required")
    private DisputeStatus newStatus;

    @Size(max = 1000, message = "Agent notes must not exceed 1000 characters")
    private String agentNotes;
}
