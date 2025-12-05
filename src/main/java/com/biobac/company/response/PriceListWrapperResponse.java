package com.biobac.company.response;

import lombok.*;

import java.util.List;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PriceListWrapperResponse {
    private Long id;
    private String name;
    private List<PriceListItemResponse> priceListItems;
}
