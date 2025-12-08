package com.biobac.company.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class PreSaleRequest {
    private Long ourCompanyId;
    private Long buyerCompanyId;
    private List<SaleItemRequest> items;
    private BigDecimal totalAmount;
}
