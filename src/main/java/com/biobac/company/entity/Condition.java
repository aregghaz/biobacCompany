package com.biobac.company.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "conditions")
public class Condition extends BaseEntity {

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
