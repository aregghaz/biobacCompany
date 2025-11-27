package com.biobac.company.response;

import com.biobac.company.entity.Cooperation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
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
    private boolean advancePayment;
    private String localAddress;
    private String actualAddress;
    private String warehouseAddress;
    private List<Long> attributeGroupIds;
    private CompanyGroupResponse companyGroup;
    private SaleTypeResponse saleType;
    private Set<String> emails;
    private List<String> phones;
    private Set<String> externalEmails;
    private List<String> externalPhones;
    private Set<String> websites;
    private Set<String> addressTT;
    private RegionResponse region;
    private List<CompanyTypeResponse> types;
    private ClientTypeResponse clientType;
    private List<LineResponse> lines;
    private Cooperation cooperation;
    private List<ContactPersonResponse> contactPerson;
    private DetailsResponse detail;
    private ConditionsResponse condition;
    private SourceResponse source;
    private Long responsibleEmployeeId;
    private LocalDateTime ogrnDate;
    private LocalDateTime clientRegisteredDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String seo;
}
