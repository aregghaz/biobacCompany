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
    private List<DeliveryMethodResponse> deliveryMethod;
    private Long deliveryPayerId;
    private String deliveryPayerName;
    private List<FinancialTermsResponse> financialTerm;
    private Long contractFormId;
    private String contractFormName;
    private Double bonus;
}

