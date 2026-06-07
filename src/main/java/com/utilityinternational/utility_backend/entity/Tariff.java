package com.utilityinternational.utility_backend.entity;

import com.utilityinternational.utility_backend.enums.UtilityType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tariffs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tariff extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String tariffName;

    @Enumerated(EnumType.STRING)
    private UtilityType utilityType;

    @Column(nullable = false)
    private Double ratePerUnit;
}