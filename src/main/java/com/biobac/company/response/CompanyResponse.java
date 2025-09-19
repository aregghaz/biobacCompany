package com.biobac.company.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CompanyResponse {
    private Long id;
    private String name;
    private String address;
    private String phoneNumber;
    private String managerNumber;
    private RegionResponse region;
    private List<Long> attributeGroupIds;
    private String email;
    private String website;
    private List<CompanyTypeResponse> types;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<AttributeResponse> attributes;
}
