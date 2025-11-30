package com.biobac.company.entity;

import com.biobac.company.entity.embeddable.BankInfo;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Account extends BaseEntity {
    private Long id;
    private String name;
    private BigDecimal balance;

    @Embedded
    private BankInfo bankInfo;

    @ManyToOne
    @JoinColumn(name = "our_company_id")
    private OurCompany ourCompany;
}
