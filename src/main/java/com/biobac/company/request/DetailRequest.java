package com.biobac.company.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DetailRequest {
    private String inn;
    private String kpp;
    private String ogrn;
    private String okpo;
    private String bankAccount;
    private String bik;
    private String ks;
    private String bankName;
}
