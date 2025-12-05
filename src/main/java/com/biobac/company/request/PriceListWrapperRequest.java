package com.biobac.company.request;

import lombok.*;

import java.util.List;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PriceListWrapperRequest {
    private String name;
    private List<PriceListItemRequest> priceListItems;
}
