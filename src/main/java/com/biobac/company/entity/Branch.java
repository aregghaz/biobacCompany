package com.biobac.company.entity;

import com.biobac.company.entity.embeddable.Address;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Branch extends BaseEntity {
    private String name;

    @Embedded
    private Address address;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;
}
