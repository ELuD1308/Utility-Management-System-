package com.utilityinternational.utility_backend.repository;

import com.utilityinternational.utility_backend.entity.Payment;
import com.utilityinternational.utility_backend.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByStatus(PaymentStatus status);
}