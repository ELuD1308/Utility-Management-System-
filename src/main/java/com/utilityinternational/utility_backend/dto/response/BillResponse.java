package com.utilityinternational.utility_backend.dto.response;

import com.utilityinternational.utility_backend.enums.BillStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillResponse {

    private Long id;
    private String billNumber;
    private LocalDate billingPeriodStart;
    private LocalDate billingPeriodEnd;
    private LocalDate dueDate;
    private Integer totalUnitsConsumed;
    private BigDecimal amountDue;
    private BigDecimal amountPaid;
    private BigDecimal balance;
    private BillStatus status;
    private LocalDate paymentDate;
    private LocalDateTime createdAt;
}
