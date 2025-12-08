package com.biobac.company.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class SaleResponse extends AuditableResponse {
    private Long id;
    private OurCompanyResponse ourCompany;
    private CompanyResponse company;
    private SaleStatusResponse status;
    private List<SaleItemResponse> items;
    private BigDecimal totalAmount;
    private BigDecimal receivedAmount;
}
