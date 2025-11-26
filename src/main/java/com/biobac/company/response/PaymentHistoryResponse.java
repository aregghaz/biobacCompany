package com.biobac.company.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class PaymentHistoryResponse extends AuditableResponse {
    private LocalDateTime date;
    private AccountResponse account;
    private PaymentCategoryResponse paymentCategory;
    private boolean increased;
    private String notes;
    private BigDecimal sum;
    private String username;
}
