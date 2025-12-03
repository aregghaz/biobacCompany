package com.biobac.company.request;

import com.biobac.company.entity.embeddable.BankInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OurCompanyRequest {
    private String name;
    private String localAddress;
    private String actualAddress;
    private String warehouseAddress;
    private List<Long> attributeGroupIds;
    private List<String> emails;
    private List<String> websites;
    private List<String> phones;
    private DetailsRequest detail;
    private List<BankInfo> bankInformationList;
    private List<Long> accountIds;
    private List<AttributeUpsertRequest> attributes;
}
