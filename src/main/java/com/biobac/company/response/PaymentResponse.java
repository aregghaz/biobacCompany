package com.biobac.company.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class PaymentResponse extends AuditableResponse {
    private Long id;
    private LocalDateTime date;
    private AccountResponse account;
    private PaymentCategoryResponse paymentCategory;
    private String notes;
    private BigDecimal sum;
}
