package com.utilityinternational.utility_backend.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TariffChangeRequest {

    @NotNull(message = "Target tariff ID is required")
    private Long targetTariffId;
}
