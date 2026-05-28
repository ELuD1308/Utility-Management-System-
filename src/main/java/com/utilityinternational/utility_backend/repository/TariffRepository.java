package com.utilityinternational.utility_backend.repository;

import com.utilityinternational.utility_backend.entity.Tariff;
import com.utilityinternational.utility_backend.enums.UtilityType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TariffRepository extends JpaRepository<Tariff, Long> {
     // New — needed by TariffService
    List<Tariff> findByUtilityType(UtilityType utilityType);
}