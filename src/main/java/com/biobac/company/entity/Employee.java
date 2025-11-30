package com.biobac.company.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Employee extends BaseEntity {
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
    @ManyToOne
    private OurCompany ourCompany;
}
