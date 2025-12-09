package com.biobac.company.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Sale extends BaseEntity {
    @ManyToOne(optional = false)
    private OurCompany ourCompany;

    @ManyToOne(optional = false)
    private Company buyerCompany;

    private BigDecimal totalAmount;

    private BigDecimal receivedAmount;

    @OneToMany(mappedBy = "sale", fetch = FetchType.EAGER)
    private List<SaleItem> items = new ArrayList<>();

    @ManyToOne
    private SaleStatus status;

    @ManyToOne
    private ContactPerson contactPerson;

    private LocalDateTime orderDate;

    private LocalDateTime saleDate;
}
