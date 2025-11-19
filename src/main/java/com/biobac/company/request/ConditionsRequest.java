package com.biobac.company.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConditionsRequest {
    private Long deliveryMethodId;
    private Long deliveryPayerId;
    private Long financialTermsId;
    private Long contractFormId;
    private Double bonus;
}
