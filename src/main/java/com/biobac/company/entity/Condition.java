package com.biobac.company.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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
@Table(name = "conditions")
public class Condition extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @ManyToMany
    @JoinTable(
            name = "condition_delivery_method",
            joinColumns = @JoinColumn(name = "condition_id"),
            inverseJoinColumns = @JoinColumn(name = "delivery_method_id")
    )
    private List<DeliveryMethod> deliveryMethod;

    @ManyToOne
    private DeliveryPayer deliveryPayer;



    @ManyToMany
    @JoinTable(
            name = "conditions_financial_terms",
            joinColumns = @JoinColumn(name = "condition_id"),
            inverseJoinColumns = @JoinColumn(name = "financial_terms_id")
    )
    private List<FinancialTerms> financialTerms;

    @ManyToOne
    private ContractForm contractForm;

    private Double bonus;
}
