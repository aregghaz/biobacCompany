package com.biobac.company.request;

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
public class CompanyRequest {
    private String name;
    private boolean advancePayment;
    private String localAddress;
    private String actualAddress;
    private String warehouseAddress;
    private List<Long> attributeGroupIds;
    private Long companyGroupId;
    private Long saleTypeId;
    private Set<String> emails;
    private Set<String> externalEmails;
    private Set<String> website;
    private List<String> phones;
    private List<String> externalPhones;
    private Long regionId;
    private List<Long> typeIds;
    private BigDecimal balance;
    private BigDecimal bonus;
    private String generalDirector;
    private Long customerTypeId;
    private Long lineId;
    private Long cooperationId;
    private List<Long> contactPersonIds;
    private DetailRequest detail;
    private ConditionsRequest condition;
}
