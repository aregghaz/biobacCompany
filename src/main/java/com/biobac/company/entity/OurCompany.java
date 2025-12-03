package com.biobac.company.entity;

import com.biobac.company.entity.embeddable.Address;
import com.biobac.company.entity.embeddable.BankInfo;
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

    @ElementCollection
    private List<BankInfo> bankInformationList;

    @OneToMany(mappedBy = "ourCompany", fetch = FetchType.LAZY)
    private List<Account> accounts;
}
