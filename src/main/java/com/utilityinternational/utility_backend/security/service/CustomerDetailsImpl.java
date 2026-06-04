package com.utilityinternational.utility_backend.security.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.utilityinternational.utility_backend.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public class CustomerDetailsImpl implements UserDetails {

    private Long id;

    private String fullName;

    private String email;

    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    public static CustomerDetailsImpl build(Customer customer) {

        List<GrantedAuthority> authorities =
                customer.getRoles().stream()
                        .map(role ->
                                new SimpleGrantedAuthority(
                                        role.getName().name()
                                )
                        )
                        .collect(Collectors.toList());

        return new CustomerDetailsImpl(
                customer.getId(),
                customer.getFullName(),
                customer.getEmail(),
                customer.getPassword(),
                authorities
        );
    }

    @Override
    public String getUsername() {

        // Using email as login username
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {

        return !false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}