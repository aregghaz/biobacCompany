package com.biobac.company.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class FinalizeSaleRequest {
    private Long id;
    private LocalDateTime saleDate;
    private BigDecimal receivedAmount;
}
