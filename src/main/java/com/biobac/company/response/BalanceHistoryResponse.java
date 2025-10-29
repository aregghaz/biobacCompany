package com.biobac.company.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class BalanceHistoryResponse {
    private String name;
    private String action;
    private LocalDateTime timestamp;
    private BigDecimal quantityBefore;
    private BigDecimal quantityAfter;
}
