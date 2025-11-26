package com.biobac.company.dto;

import com.biobac.company.entity.embeddable.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CompanyDto {
    private String name;
    private Address address;
    private String phoneNumber;
    private String email;
    private String website;
}

