package com.biobac.company.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyHistoryResponse extends AuditableResponse {
    private Double before;
    private Double after;
    private String note;
    private LocalDateTime timestamp;
}
