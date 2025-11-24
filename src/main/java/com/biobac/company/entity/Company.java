package com.biobac.company.entity;

import com.biobac.company.entity.embeddable.Address;
import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Company extends BaseEntity {

    private String name;
    private boolean advancePayment;
    private List<Long> attributeGroupIds;
    private String generalDirector;

    @ManyToOne
    private CompanyGroup companyGroup;

    @ManyToOne
    private SaleType saleType;

    @ElementCollection
    private Set<String> emails;

    @ElementCollection
    @CollectionTable(name = "company_phone", joinColumns = @JoinColumn(name = "company_id"))
    private List<String> phones;

    @ElementCollection
    private Set<String> externalEmails;

    @ElementCollection
    private List<String> externalPhones;

    @ElementCollection
    private Set<String> website;

    @ElementCollection
    private Set<String> addressTT;

    @ManyToOne
    @JoinColumn(name = "region_id")
    private Region region;

    @ManyToMany
    @JoinTable(
            name = "company_types_mapping",
            joinColumns = @JoinColumn(name = "company_id"),
            inverseJoinColumns = @JoinColumn(name = "type_id")
    )
    private List<CompanyType> types;
    private BigDecimal balance;
    private BigDecimal bonus;
    private boolean deleted = false;

    @Embedded
    private Address address;

    @ManyToOne
    private ClientType customerType;

    @ManyToOne
    private Line line;

    @ManyToOne
    private Cooperation cooperation;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ContactPerson> contactPerson;

    @OneToOne(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private Detail detail;

    @OneToOne(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private Condition condition;
}
