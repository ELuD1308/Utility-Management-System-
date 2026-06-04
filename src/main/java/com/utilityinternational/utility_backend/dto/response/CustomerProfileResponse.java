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
public class CustomerProfileResponse {

    private Long customerId;
    private Long userId;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String accountNumber;
    private String address;
    private String city;
    private String state;
    private String postalCode;
    private TariffResponse currentTariff;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
