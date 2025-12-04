package com.biobac.company.request;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateCompanyTypeRequest {
    private String type;
}
