package com.biobac.company.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
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

    @ElementCollection
    private List<String> phones;

    @ElementCollection
    private List<String> emails;
    private String position;

    @ManyToMany(mappedBy = "contactPerson", fetch = FetchType.LAZY)
    private List<Company> company;

    private String notes;
    private LocalDateTime dob;
}
