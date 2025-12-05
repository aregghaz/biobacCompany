package com.biobac.company.request;

import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PriceListItemRequest {
    private Long id;
    private BigDecimal price;
}
