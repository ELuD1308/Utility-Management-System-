package com.utilityinternational.utility_backend.repository;

import com.utilityinternational.utility_backend.entity.TariffHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TariffHistoryRepository extends JpaRepository<TariffHistory, Long> {

    Page<TariffHistory> findByCustomerIdOrderByChangedAtDesc(Long customerId, Pageable pageable);
}
