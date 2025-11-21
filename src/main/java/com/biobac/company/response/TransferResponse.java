package com.biobac.company.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class TransferResponse {
    private Long id;
    private AccountResponse fromAccount;
    private AccountResponse toAccount;
    private String notes;
    private LocalDateTime date;
    private BigDecimal sum;
}
