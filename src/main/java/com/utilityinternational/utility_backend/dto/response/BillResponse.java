package com.utilityinternational.utility_backend.dto.response;

import com.utilityinternational.utility_backend.enums.BillStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class BillResponse {

    private Long billId;
    private BigDecimal amount;
    private LocalDate dueDate;
    private BillStatus status;
    private boolean overdue;
    private LocalDateTime createdAt;
}
