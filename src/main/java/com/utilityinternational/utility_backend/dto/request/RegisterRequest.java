package com.utilityinternational.utility_backend.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
 
import java.util.Set;
 
@Data
public class RegisterRequest {
 
    @NotBlank(message = "Full name is required")
    @Size(min = 3, max = 50, message = "Full name must be between 3 and 50 characters")
    private String fullName;
 
    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;
 
    @NotBlank(message = "Password is required")
    @Size(min = 4, max = 40, message = "Password must be between 4 and 40 characters")
    private String password;
 
    private String phoneNumber;
 
    private Set<String> roles;
}