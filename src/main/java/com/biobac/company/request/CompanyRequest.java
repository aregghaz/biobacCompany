package com.biobac.company.request;

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
    private Long regionId;

    @NotNull(message = "Types ID should not be null")
    private List<Long> typeIds;

    @JsonProperty("clientTypeId")
    @NotNull(message = "client type employee ID should not be null")
    @Min(value = 1, message = "Responsible employee ID must be greater than 0")
    private Long clientTypeId;
    private List<Long> lineIds;

    @NotNull(message = "client type employee ID should not be null")
    @Min(value = 1, message = "Responsible employee ID must be greater than 0")
    private Long cooperationId;
    private List<Long> contactPersonIds;
    private DetailRequest detail;
    private ConditionsRequest condition;

    @NotNull(message = "Source ID should not be null")
    @Min(value = 1, message = "Source ID must be greater than 0")
    private Long sourceId;

    @NotNull(message = "Responsible employee ID should not be null")
    @Min(value = 1, message = "Responsible employee ID must be greater than 0")
    private Long responsibleEmployeeId;

    @NotNull(message = "OGRN date should not be null")
    private LocalDateTime ogrnDate;
    private LocalDateTime clientRegisteredDate;
    private String seo;
}
