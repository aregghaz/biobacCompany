package com.biobac.company.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
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

    @ManyToMany(mappedBy = "contactPerson")
    private List<Company> company;
}
