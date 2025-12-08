package com.biobac.company.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductConsumeSaleRequest {
    private Long productId;
    private Double quantity;
}
