package com.biobac.company.response;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FnsCompanyResponse {
    private String name;
    private String fullAddress;
    private String regionCode;

    private String inn;
    private String kpp;
    private String ogrn;
    private String okpo;

    private String phone;
    private String email;
    private String website;

    private String director;
}
