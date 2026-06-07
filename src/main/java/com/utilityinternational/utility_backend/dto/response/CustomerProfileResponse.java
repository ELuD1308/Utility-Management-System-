package com.utilityinternational.utility_backend.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CustomerProfileResponse {

    private Long customerId;
    private String fullName;
    private String email;
    private LocalDateTime memberSince;
}
