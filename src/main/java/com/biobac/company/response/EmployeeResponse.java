package com.biobac.company.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class EmployeeResponse {
    private String firstname;
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
    private String organization;
    private DepartmentResponse department;
    private OurCompanyResponse ourCompany;
    private String jobTitle;
    private LocalDateTime dateOfEmployment;
    private LocalDateTime dismissalDate;
    private String wages;
    private String cart;
    private String cash;
    private String martialStatus;
    private String spouseFullName;
    private String children;
}
