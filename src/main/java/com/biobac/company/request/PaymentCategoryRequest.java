package com.biobac.company.request;

import com.biobac.company.entity.enums.Category;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentCategoryRequest {
    private String name;
    private Long parentId;
    private Category category;
}
