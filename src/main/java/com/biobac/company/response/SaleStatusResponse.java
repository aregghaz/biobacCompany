package com.biobac.company.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaleStatusResponse extends AuditableResponse {
    private Long id;
    private String name;
}
