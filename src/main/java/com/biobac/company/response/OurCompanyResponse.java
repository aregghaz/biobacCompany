package com.biobac.company.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OurCompanyResponse extends AuditableResponse {
    private Long id;
    private String name;
    private List<AccountResponse> accounts;
}
