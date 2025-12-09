package com.biobac.company.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class SaleItemResponse extends AuditableResponse {
    private Long id;
    private SimpleNameResponse product;
    private Double quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
}
