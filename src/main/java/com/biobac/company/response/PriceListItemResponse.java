package com.biobac.company.response;

import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PriceListItemResponse {
    private Long id;
    private BigDecimal price;
}
