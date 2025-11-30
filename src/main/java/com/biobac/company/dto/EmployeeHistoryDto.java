package com.biobac.company.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeHistoryDto {
    private Long employeeId;

    private String wagesBefore;
    private String wagesAfter;

    private String cashBefore;
    private String cashAfter;

    private String cartBefore;
    private String cartAfter;
}
