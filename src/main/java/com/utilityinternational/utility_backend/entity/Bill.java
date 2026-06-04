package com.utilityinternational.utility_backend.entity;

import com.utilityinternational.utility_backend.enums.BillStatus;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "bills")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Bill extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private LocalDate dueDate;

    private String billNumber;

    private LocalDate billingPeriodStart;

    private LocalDate billingPeriodEnd;

    private Integer totalUnitsConsumed;

    private BigDecimal amountPaid = BigDecimal.ZERO;

    private LocalDate paymentDate;

    @Enumerated(EnumType.STRING)
    private BillStatus status;

    @Transient
    public BigDecimal getAmountDue() {
        return amount;
    }

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
}