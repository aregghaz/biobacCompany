package com.biobac.company.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Details {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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
