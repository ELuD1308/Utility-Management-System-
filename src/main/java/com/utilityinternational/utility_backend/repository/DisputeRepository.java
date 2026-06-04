package com.utilityinternational.utility_backend.repository;

import com.utilityinternational.utility_backend.entity.Dispute;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DisputeRepository extends JpaRepository<Dispute, Long> {
}