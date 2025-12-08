package com.biobac.company.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class SaleItemRequest {
    private Long productId;
    private Double quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
}
