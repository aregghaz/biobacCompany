package com.biobac.company.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyGroupResponse extends AuditableResponse {
    private Long id;
    private String name;
}
