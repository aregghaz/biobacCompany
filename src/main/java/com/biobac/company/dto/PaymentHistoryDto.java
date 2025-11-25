package com.biobac.company.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class PaymentHistoryDto {
    private LocalDateTime date;
    private Long accountId;
    private Long paymentCategoryId;
    private boolean increased;
    private String notes;
    private BigDecimal sum;
    private Long userId;
}
