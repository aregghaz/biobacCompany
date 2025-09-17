package com.biobac.company.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CompanyRequest {
    private String name;
    private String address;
    private String phoneNumber;
    private String managerNumber;
    private String email;
    private String website;
    private List<Long> typeIds;
    private List<AttributeUpsertRequest> attributes;
}
