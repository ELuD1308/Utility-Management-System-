package com.utilityinternational.utility_backend.repository;

import com.utilityinternational.utility_backend.entity.Bill;
import com.utilityinternational.utility_backend.enums.BillStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import java.util.Optional;
public interface BillRepository extends JpaRepository<Bill, Long> {
    List<Bill> findByCustomerId(Long customerId);

    List<Bill> findByStatus(BillStatus status);
    // New — needed by DisputeService and CustomerService
    Optional<Bill> findByIdAndCustomerId(Long id, Long customerId);
    List<Bill> findByCustomerIdAndStatus(Long customerId, BillStatus status);
}