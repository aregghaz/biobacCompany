package com.biobac.company.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class AccountResponse {
    private Long id;
    private String name;
    private BigDecimal balance;
    private String bankAccount;
    private String bik;
    private String ks;
    private String bankName;
}
