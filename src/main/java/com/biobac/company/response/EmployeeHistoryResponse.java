package com.biobac.company.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class EmployeeHistoryResponse extends AuditableResponse {
    private LocalDateTime timestamp;
    private String notes;
    private BigDecimal quantityBefore;
    private BigDecimal quantityAfter;
    private EmployeeResponse employee;
}
