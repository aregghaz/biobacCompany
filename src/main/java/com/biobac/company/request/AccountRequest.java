package com.biobac.company.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountRequest {
    private String name;
    private String bankAccount;
    private String bik;
    private String ks;
    private String bankName;
}
