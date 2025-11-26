package com.biobac.company.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class PaymentRequest {
    private LocalDateTime date;
    private Long accountId;
    private BigDecimal sum;
    private String notes;
    private Long paymentCategoryId;
}
