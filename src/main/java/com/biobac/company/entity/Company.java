package com.biobac.company.entity;

import com.biobac.company.entity.embeddable.Address;
import com.biobac.company.entity.embeddable.BankInfo;
import com.biobac.company.entity.embeddable.Location;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
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

    @ManyToOne
    private CompanyGroup companyGroup;

    @ManyToOne
    private SaleType saleType;

    @ElementCollection
    private Set<String> emails;

    private boolean deleted = false;

    @ElementCollection
    @CollectionTable(name = "company_phone", joinColumns = @JoinColumn(name = "company_id"))
    private List<String> phones;

    @ElementCollection
    @CollectionTable(name = "company_external_email", joinColumns = @JoinColumn(name = "company_id"))
    private Set<String> externalEmails;

    @ElementCollection
    @CollectionTable(name = "company_external_phone", joinColumns = @JoinColumn(name = "company_id"))
    private List<String> externalPhones;

    @ElementCollection
    @CollectionTable(name = "company_websites", joinColumns = @JoinColumn(name = "company_id"))
    private Set<String> websites;

    @ElementCollection
    @CollectionTable(name = "company_address_tt", joinColumns = @JoinColumn(name = "company_id"))
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

    @Embedded
    private Address address;

    @ManyToOne
    private ClientType clientType;

    @OneToMany
    @JoinColumn(name = "company_id")
    private List<Line> lines;

    @ManyToOne
    private Cooperation cooperation;

    @ManyToMany
    @JoinTable(
            name = "contact_person_company",
            joinColumns = @JoinColumn(name = "company_id"),
            inverseJoinColumns = @JoinColumn(name = "contact_person_id")
    )
    private List<ContactPerson> contactPerson;

    @OneToOne(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private Detail detail;

    @OneToOne(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private Condition condition;

    @ElementCollection
    private List<BankInfo> bankInformationList;

    @Embedded
    private Location location;
    private String chainOfStore;

    @ManyToOne
    private Source source;
    private Long responsibleEmployeeId;
    private LocalDateTime ogrnDate;
    private LocalDateTime clientRegisteredDate;
    private String seo;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Branch> branches;
}
