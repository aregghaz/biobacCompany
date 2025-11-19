package com.biobac.company.response;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CompanyResponse extends AuditableResponse {
    private Long id;
    private String name;
    private String legalAddress;
    private String actualAddress;
    private String warehouseAddress;
    private String phoneNumber;
    private String managerNumber;

    private RegionResponse region;
    private SaleTypeResponse saleType;
    private List<Long> attributeGroupIds;
    private String email;
    private String website;
    private boolean advancePayment;
    private List<CompanyTypeResponse> types;
    private List<AttributeResponse> attributes;
    private BigDecimal balance;
    private DetailsResponse details;
    private ConditionsResponse conditions;
    @JsonUnwrapped(prefix = "cooperation")
    private EntityReferenceResponse cooperation;
    @JsonUnwrapped(prefix = "line")
    private EntityReferenceResponse line;
    @JsonUnwrapped(prefix = "clientType")
    private EntityReferenceResponse clientType;
    @JsonUnwrapped(prefix = "companyGroup")
    private EntityReferenceResponse companyGroup;
}
