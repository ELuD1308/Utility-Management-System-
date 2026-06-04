package com.utilityinternational.utility_backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tariff_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TariffHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "previous_tariff_id")
    private Tariff previousTariff;

    @ManyToOne
    @JoinColumn(name = "new_tariff_id", nullable = false)
    private Tariff newTariff;

    private String changedBy;

    private String changeReason;

    private LocalDateTime changedAt;
}
