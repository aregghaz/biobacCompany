package com.biobac.company.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
public class Company extends BaseAuditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String address;
    private String phoneNumber;
    private boolean advancePayment;
    private String managerNumber;
    private List<Long> attributeGroupIds;
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
}
