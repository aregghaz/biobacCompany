package com.biobac.company.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConditionsRequest {
    private List<Long> deliveryMethodId;
    private Long deliveryPayerId;
    private List<Long> financialTermsId;
    private Long contractFormId;
    private Double bonus;
}
