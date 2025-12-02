package com.biobac.company.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class AccountRequest {
    private String name;
    private String bankAccount;
    private String bik;
    private String ks;
    private String bankName;
    private Long ourCompanyId;
}
