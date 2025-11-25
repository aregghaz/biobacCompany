package com.biobac.company.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentCategoryRequest {
    private String name;
    private Long parentId;
}
