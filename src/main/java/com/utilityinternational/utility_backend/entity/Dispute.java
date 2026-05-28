package com.utilityinternational.utility_backend.entity;

import com.utilityinternational.utility_backend.enums.DisputeStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "disputes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Dispute extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String disputeReason;

    @Enumerated(EnumType.STRING)
    private DisputeStatus status;

    @OneToOne
    @JoinColumn(name = "bill_id")
    private Bill bill;
}