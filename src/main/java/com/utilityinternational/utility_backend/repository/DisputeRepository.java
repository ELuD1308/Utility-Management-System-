package com.utilityinternational.utility_backend.repository;

import com.utilityinternational.utility_backend.entity.Dispute;
import com.utilityinternational.utility_backend.enums.DisputeStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
public interface DisputeRepository extends JpaRepository<Dispute, Long> {

    List<Dispute> findByBillCustomerId(Long customerId);

    List<Dispute> findByStatus(DisputeStatus status);

    Optional<Dispute> findByBillId(Long billId);

    boolean existsByBillId(Long billId);
}