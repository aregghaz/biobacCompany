package com.biobac.company.response;

import com.biobac.company.entity.Cooperation;
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
    private String name;
    private boolean advancePayment;
    private String localAddress;
    private String actualAddress;
    private String warehouseAddress;
    private List<Long> attributeGroupIds;
    private String generalDirector;
    private CompanyGroupResponse companyGroup;
    private SaleTypeResponse saleType;
    private Set<String> emails;
    private List<String> phones;
    private Set<String> externalEmails;
    private List<String> externalPhones;
    private Set<String> website;
    private Set<String> addressTT;
    private RegionResponse region;
    private List<CompanyTypeResponse> types;
    private BigDecimal balance;
    private BigDecimal bonus;
    private boolean deleted = false;
    private ClientTypeResponse customerType;
    private LineResponse line;
    private Cooperation cooperation;
    private List<ContactPersonResponse> contactPerson;
    private DetailsResponse detail;
    private ConditionsResponse condition;

}
