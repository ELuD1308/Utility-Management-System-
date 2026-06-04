package com.utilityinternational.utility_backend.entity;

import com.utilityinternational.utility_backend.enums.UtilityType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "utility_usage")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UtilityUsage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate readingDate;

    private Integer unitsConsumed;

    private Integer meterReadingStart;

    private Integer meterReadingEnd;

    @Enumerated(EnumType.STRING)
    private UtilityType utilityType;

    private LocalDate recordedAt;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
}
