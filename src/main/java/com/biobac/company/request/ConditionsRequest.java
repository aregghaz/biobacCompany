package com.biobac.company.request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConditionsRequest {

    private List<Long> deliveryMethodIds;
    private Long deliveryPayerId;
    private List<Long> financialTermIds;
    private Long contractFormId;
    private Double bonus;
}
