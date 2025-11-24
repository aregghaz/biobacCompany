package com.biobac.company.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyResponse {
    private Long id;
    private String name;
    private String localAddress;
    private String actualAddress;
    private String warehouseAddress;
    private List<String> phones;
    private List<String> externalPhones;
    private String managerNumber;
    private RegionResponse region;
    private SaleTypeResponse saleType;
    private List<Long> attributeGroupIds;
    private Set<String> emails;
    private Set<String> externalEmails;
    private Set<String> website;
    private String generalDirector;
    private boolean advancePayment;
    private List<CompanyTypeResponse> types;
    private List<AttributeResponse> attributes;
    private BigDecimal balance;
    private DetailsResponse detail;
    private ConditionsResponse condition;
    private CompanyGroupResponse companyGroup;
    private ClientTypeResponse customerType;
    private LineResponse line;
    private CooperationResponse cooperation;

}
