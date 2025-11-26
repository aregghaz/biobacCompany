package com.biobac.company.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private List<String> phones;
    private Set<String> externalEmails;
    private List<String> externalPhones;
    private Set<String> websites;
    private List<AttributeUpsertRequest> attributes;
    private Set<String> addressTT;
    private Long regionId;
    private List<Long> typeIds;

    @JsonProperty("clientTypeId")
    private Long customerTypeId;
    private List<Long> lineIds;
    private Long cooperationId;
    private List<Long> contactPersonIds;
    private DetailRequest detail;
    private ConditionsRequest condition;
    private Long sourceId;
    private Long responsibleEmployeeId;
    private String seo;
}
