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
    private String generalDirector;
    private Long companyGroupId;
    private Long saleTypeId;
    private Set<String> emails;
    private List<String> phones;
    private Set<String> externalEmails;
    private List<String> externalPhones;
    private Set<String> website;
    private List<AttributeUpsertRequest> attributes;
    private Set<String> addressTT;
    private Long regionId;
    private List<Long> typeIds;
    private BigDecimal balance;
    private BigDecimal bonus;
    private boolean deleted = false;
    private Long customerTypeId;
    private Long lineId;
    private Long cooperationId;
    private List<Long> contactPersonIds;
    private DetailRequest detail;
    private ConditionsRequest condition;
}
