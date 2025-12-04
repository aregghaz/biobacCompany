package com.biobac.company.dto;

import lombok.*;

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
