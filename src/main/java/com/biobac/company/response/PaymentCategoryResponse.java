package com.biobac.company.response;

import com.biobac.company.entity.enums.Category;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentCategoryResponse extends AuditableResponse {
    private Long id;
    private Long parentId;
    private PaymentCategoryResponse parent;
    private String name;
    private Category category;
    private List<PaymentCategoryResponse> children;
}
