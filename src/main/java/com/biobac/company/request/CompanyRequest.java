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
    private String legalAddress;
    private String actualAddress;
    private String warehouseAddress;
    private String phoneNumber;
    private List<Long> attributeGroupIds;
    private Long regionId;
    private boolean advancePayment;
    private Long saleTypeId;
    private Long lineId;
    private Long clientTypeId;
    private Long cooperationId;
    private String managerNumber;
    private String email;
    private String website;
    private List<Long> typeIds;
    private List<AttributeUpsertRequest> attributes;
    private Long companyGroupId;
    private ConditionsRequest condition;
    private DetailsRequest detail;
}
