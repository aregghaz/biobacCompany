package com.biobac.company.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ContactPerson extends BaseEntity {
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String position;

    @ManyToMany
    @JoinTable(
            name = "contact_person_company",
            joinColumns = @JoinColumn(name = "contact_person_id"),
            inverseJoinColumns = @JoinColumn(name = "company_id")
    )
    private List<Company> company;
}
