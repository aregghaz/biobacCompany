package com.biobac.company.response;

import com.biobac.company.entity.enums.Category;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PaymentCategoryResponse extends AuditableResponse{
    private Long id;
    private Long parentId;
    private PaymentCategoryResponse parent;
    private String name;
    private Category category;
    private List<PaymentCategoryResponse> children;
}
