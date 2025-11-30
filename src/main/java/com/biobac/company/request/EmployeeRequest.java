package com.biobac.company.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class EmployeeRequest {

    @NotEmpty(message = "Firstname is required")
    @NotNull(message = "Firstname should not be null")
    private String firstname;

    @NotEmpty(message = "Lastname is required")
    @NotNull(message = "Lastname should not be null")
    private String lastname;
    private String middlename;
    private String residentialAddress;
    private String registrationAddress;
    private String emergencyContact;
    private String emergencyContactPhone;
    private LocalDateTime dob;
    private String inn;
    private String snils;
    private String citizenship;
    private String passportSeries;
    private String passportNumber;
    private String passportIssuedBy;
    private Long departmentId;
    private String jobTitle;
    private LocalDateTime dateOfEmployment;
    private LocalDateTime dismissalDate;
    private String wages;
    private String cart;
    private String cash;
    private String maritalStatus;
    private String spouseFullName;
    private String children;
    private Long ourCompanyId;
}
