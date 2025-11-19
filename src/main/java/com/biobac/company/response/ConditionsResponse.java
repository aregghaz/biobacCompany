package com.biobac.company.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConditionsResponse {
    private Long deliveryMethodId;
    private String deliveryMethodName;

    private Long deliveryPayerId;
    private String deliveryPayerName;

    private Long financialTermsId;
    private String financialTermsName;

    private Long contractFormId;
    private String contractFormName;

    private Double bonus;
}

