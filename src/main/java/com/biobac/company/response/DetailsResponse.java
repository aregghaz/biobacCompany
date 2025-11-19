package com.biobac.company.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DetailsResponse {
    private String inn;
    private String kpp;
    private String ogrn;
    private String okpo;
    private String bankAccount;
    private String bik;
    private String ks;
    private String bankName;
}

