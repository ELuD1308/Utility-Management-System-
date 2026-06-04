package com.utilityinternational.utility_backend.repository;

import com.utilityinternational.utility_backend.entity.UtilityUsage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface UtilityUsageRepository
        extends JpaRepository<UtilityUsage, Long> {

    Page<UtilityUsage> findByCustomerIdOrderByReadingDateDesc(Long customerId, Pageable pageable);

    List<UtilityUsage> findByCustomerId(Long customerId);

    List<UtilityUsage> findByUsageDateBetween(
            LocalDate startDate,
            LocalDate endDate
    );

    List<UtilityUsage> findByCustomerIdAndUsageDateBetween(
            Long customerId,
            LocalDate startDate,
            LocalDate endDate
    );
}