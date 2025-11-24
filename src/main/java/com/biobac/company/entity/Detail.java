package com.biobac.company.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
public class Detail extends BaseEntity {

    private String inn;
    private String kpp;
    private String ogrn;
    private String okpo;
    private String bankAccount;
    private String bik;
    private String ks;
    private String bankName;

    @OneToOne
    @JoinColumn(name = "company_id")
    private Company company;
}
