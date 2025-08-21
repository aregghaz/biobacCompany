package com.biobac.company.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CompanyDto {
    private String name;
    private String address;
    private String phoneNumber;
    private String email;
    private String website;
}

