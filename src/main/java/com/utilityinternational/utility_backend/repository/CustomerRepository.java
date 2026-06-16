package com.utilityinternational.utility_backend.repository;

import com.utilityinternational.utility_backend.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByEmail(String email);

    Optional<Customer> findByFullName(String fullName);

    // 💡 This annotation overrides Spring's automatic parsing naming rules!
    @Query("SELECT c FROM Customer c LEFT JOIN FETCH c.currentTariff WHERE c.fullName = :fullName")
    Optional<Customer> findByFullNameWithTariff(@Param("fullName") String fullName);

    Boolean existsByEmail(String email);
}