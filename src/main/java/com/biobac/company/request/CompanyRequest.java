package com.biobac.company.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class CompanyRequest {

    @NotBlank(message = "Company name should not be empty")
    private String name;
    private boolean advancePayment;
    private String localAddress;
    private String actualAddress;
    private String warehouseAddress;
    private List<Long> attributeGroupIds;

    @NotNull(message = "Company group ID should not be null")
    @Min(value = 1, message = "Company group ID must be greater than 0")
    private Long companyGroupId;
    private Long saleTypeId;
    private Set<String> emails;
    private List<String> phones;
    private Set<String> externalEmails;
    private List<String> externalPhones;
    private Set<String> websites;
    private List<AttributeUpsertRequest> attributes;
    private Set<String> addressTT;

    @NotNull(message = "Region ID should not be null")
    @Min(value = 1, message = "Region ID must be greater than 0")
    private Long regionId;
    private List<Long> typeIds;

    @JsonProperty("clientTypeId")
    @NotNull(message = "client type employee ID should not be null")
    @Min(value = 1, message = "Responsible employee ID must be greater than 0")
    private Long customerTypeId;
    private List<Long> lineIds;
    private Long cooperationId;
    private List<Long> contactPersonIds;
    private DetailRequest detail;
    private ConditionsRequest condition;
    private Long sourceId;

    @NotNull(message = "Responsible employee ID should not be null")
    @Min(value = 1, message = "Responsible employee ID must be greater than 0")
    private Long responsibleEmployeeId;

    @NotNull(message = "OGRN date should not be null")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime ogrnDate;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime clientRegisteredDate;
    private String seo;
}
