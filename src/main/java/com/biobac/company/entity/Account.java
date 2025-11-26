package com.biobac.company.entity;

import jakarta.persistence.*;
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
    private String bankAccount;
    private String bik;
    private String ks;
    private String bankName;

    @ManyToOne
    @JoinColumn(name = "our_company_id")
    private OurCompany ourCompany;
}
