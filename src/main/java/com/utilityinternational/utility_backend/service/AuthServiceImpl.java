package com.utilityinternational.utility_backend.service;

import com.utilityinternational.utility_backend.dto.request.LoginRequest;
import com.utilityinternational.utility_backend.dto.request.RegisterRequest;
import com.utilityinternational.utility_backend.dto.response.JwtResponse;
import com.utilityinternational.utility_backend.dto.response.MessageResponse;
import com.utilityinternational.utility_backend.entity.Role;
import com.utilityinternational.utility_backend.enums.ERole;
import com.utilityinternational.utility_backend.entity.Customer;
import com.utilityinternational.utility_backend.exception.ResourceNotFoundException;
import com.utilityinternational.utility_backend.exception.TokenRefreshException;
import com.utilityinternational.utility_backend.repository.RoleRepository;
import com.utilityinternational.utility_backend.repository.CustomerRepository;
import com.utilityinternational.utility_backend.security.jwt.JwtUtils;
import com.utilityinternational.utility_backend.security.service.CustomerDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
 
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
 
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
 
    private final AuthenticationManager authenticationManager;
        private final CustomerRepository customerRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
 
    @Override
    public JwtResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );
 
        SecurityContextHolder.getContext().setAuthentication(authentication);
 
        CustomerDetailsImpl CustomerDetails = (CustomerDetailsImpl) authentication.getPrincipal();
        String accessToken = jwtUtils.generateJwtToken(authentication);
        String refreshToken = jwtUtils.generateRefreshToken(CustomerDetails.getFullName());
 
        List<String> roles = CustomerDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
 
        return new JwtResponse(
                accessToken,
                refreshToken,
                CustomerDetails.getId(),
                CustomerDetails.getFullName(),
                CustomerDetails.getEmail(),
                roles
        );
    }
 
    @Override
    @Transactional
    public MessageResponse register(RegisterRequest request) {
        if (customerRepository.findByFullName(request.getFullName()).isPresent()) {
            throw new IllegalArgumentException(
                    "Full name '" + request.getFullName() + "' is already taken");
        }
        if (customerRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException(
                    "Email '" + request.getEmail() + "' is already in use");
        }
 
        Customer customer = Customer.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phoneNumber(request.getPhoneNumber())
                .build();
 
        Set<String> requestedRoles = request.getRoles();
        Set<Role> roles = new HashSet<>();
 
                if (requestedRoles == null || requestedRoles.isEmpty()) {
                        roles.add(getRole(ERole.ROLE_CUSTOMER));
        } else {
            requestedRoles.forEach(roleName -> {
                switch (roleName.toUpperCase()) {
                    case "ROLE_CALL_CENTER_AGENT" ->
                        roles.add(getRole(ERole.ROLE_CALL_CENTER_AGENT));
                    default ->
                        roles.add(getRole(ERole.ROLE_CUSTOMER));
                }
            });
        }
 
        customer.setRoles(roles);
        customerRepository.save(customer);
 
        return new MessageResponse("Customer registered successfully!");
    }
 
    @Override
    public JwtResponse refreshToken(String refreshToken) {
        if (!jwtUtils.validateJwtToken(refreshToken)) {
            throw new TokenRefreshException(refreshToken, "Refresh token is invalid or expired");
        }
 
        String fullName = jwtUtils.getUsernameFromJwtToken(refreshToken);
        customerRepository.findByFullName(fullName)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Customer not found with full name: " + fullName));

        String newAccessToken = jwtUtils.generateTokenFromUsername(fullName);
        String newRefreshToken = jwtUtils.generateRefreshToken(fullName);

        return JwtResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .tokenType("Bearer")
                .build();
    }

    private Role getRole(ERole eRole) {
        return roleRepository.findByName(eRole)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Role not found with name: " + eRole.name()));
    }
}