package com.utilityinternational.utility_backend.repository;

import com.utilityinternational.utility_backend.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByEmail(String email);

    Optional<Customer> findByFullName(String fullName);

    Optional<Customer> findByFullNameWithTariff(String fullName);

    Boolean existsByEmail(String email);
}