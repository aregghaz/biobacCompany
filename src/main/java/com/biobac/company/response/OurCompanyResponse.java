package com.biobac.company.response;

import com.biobac.company.entity.embeddable.BankInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OurCompanyResponse extends AuditableResponse {
    private Long id;
    private String name;
    private String localAddress;
    private String actualAddress;
    private String warehouseAddress;
    private List<Long> attributeGroupIds;
    private List<String> emails;
    private List<String> websites;
    private List<String> phones;
    private DetailsResponse detail;
    private List<BankInfo> bankInformationList;
    private List<AccountResponse> accounts;
    private List<AttributeResponse> attributes;
}
