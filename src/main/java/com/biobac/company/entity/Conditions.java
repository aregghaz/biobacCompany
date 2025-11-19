package com.biobac.company.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Conditions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @ManyToOne
    private DeliveryMethod deliveryMethod;

    @ManyToOne
    private DeliveryPayer deliveryPayer;

    @ManyToOne
    private FinancialTerms financialTerms;

    @ManyToOne
    private ContractForm contractForm;

    private Double bonus;
}
