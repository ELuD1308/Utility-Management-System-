package com.utilityinternational.utility_backend.repository;

import com.utilityinternational.utility_backend.entity.Bill;
import com.utilityinternational.utility_backend.enums.BillStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BillRepository extends JpaRepository<Bill, Long> {
    Page<Bill> findByCustomerIdOrderByCreatedAtDesc(Long customerId, Pageable pageable);

    List<Bill> findByCustomerId(Long customerId);

    Optional<Bill> findByCustomerIdAndId(Long customerId, Long id);

    List<Bill> findByStatus(BillStatus status);
}