package com.biobac.company.entity;

import com.biobac.company.entity.embeddable.Address;
import jakarta.persistence.*;
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
public class OurCompany extends BaseEntity {
    private String name;

    @Embedded
    private Address address;

    private List<Long> attributeGroupIds;

    @ElementCollection
    private List<String> emails;

    @ElementCollection
    private List<String> websites;

    @ElementCollection
    private List<String> phones;

    @OneToOne(mappedBy = "ourCompany", cascade = CascadeType.ALL, orphanRemoval = true)
    private Detail detail;

    @OneToMany(mappedBy = "ourCompany", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Account> accounts;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;
}
