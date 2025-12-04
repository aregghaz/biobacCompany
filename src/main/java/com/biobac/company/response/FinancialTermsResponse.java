package com.biobac.company.response;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FinancialTermsResponse {
    private Long id;
    private String name;
}
