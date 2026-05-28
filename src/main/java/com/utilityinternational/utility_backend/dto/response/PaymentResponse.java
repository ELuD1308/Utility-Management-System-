package com.utilityinternational.utility_backend.dto.response;

import com.utilityinternational.utility_backend.enums.PaymentStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class PaymentResponse {

    private Long paymentId;
    private Long billId;
    private BigDecimal amount;
    private PaymentStatus status;
    private LocalDateTime paidAt;
}
