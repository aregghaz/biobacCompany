package com.biobac.company.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyGroupResponse {
    private Long id;
    private String name;
}
