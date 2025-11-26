package com.biobac.company.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PaymentCategoryResponse extends AuditableResponse{
    private Long id;
    private Long parentId;
    private String name;
    private List<PaymentCategoryResponse> children;
}
