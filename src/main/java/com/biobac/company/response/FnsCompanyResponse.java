package com.biobac.company.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class FnsCompanyResponse {
    private String inn;
    private String kpp;
    private String ogrn;
    private String okpo;
    private String fullName;
    private String shortName;
    private String seo;
    private List<String> websites;
    private List<String> emails;
    private List<String> phones;
    private LocalDateTime dateOfRegistration;
    private LocalDateTime dateOfOgrn;
}
