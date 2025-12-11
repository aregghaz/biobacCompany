package com.biobac.company.request;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyHistoryRequest {
    private Long companyId;
    private Double after;
    private Double before;
    private String note;
}
