package com.biobac.company.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConditionsResponse {
    private Long id;
    private List<DeliveryMethodResponse> deliveryMethods;
    private Long deliveryPayerId;
    private String deliveryPayerName;
    private List<FinancialTermsResponse> financialTerms;
    private Long contractFormId;
    private String contractFormName;
    private Double bonus;
}

