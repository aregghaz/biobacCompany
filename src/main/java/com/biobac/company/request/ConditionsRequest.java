package com.biobac.company.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConditionsRequest {
    private Long deliveryMethodId;
    private Long deliveryPayerId;
    private Long financialTermsId;
    private Long contractFormId;
    private Double bonus;
}
