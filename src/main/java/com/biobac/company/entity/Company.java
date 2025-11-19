package com.biobac.company.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Company extends BaseAuditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String legalAddress;
    private String actualAddress;
    private String warehouseAddress;
    private String phoneNumber;
    private boolean advancePayment;
    private String managerNumber;
    private List<Long> attributeGroupIds;
    @ManyToOne
    private CompanyGroup companyGroup;
    @ManyToOne
    private SaleType saleType;
    private String email;
    private String website;
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
    private boolean deleted = false;

    @ManyToOne
    private ClientType customerType;

    @ManyToOne
    private Line line;

    @ManyToOne
    private Cooperation cooperation;

    @OneToMany(mappedBy = "company")
    private List<ContactPerson> contactPersons = new ArrayList<>();

    @OneToOne(mappedBy = "company")
    private Details details;

    @OneToOne(mappedBy = "company")
    private Conditions conditions;
}
