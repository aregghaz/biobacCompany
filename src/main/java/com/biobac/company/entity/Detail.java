package com.biobac.company.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
public class Detail extends BaseEntity {

    private String inn;
    private String kpp;
    private String ogrn;
    private String okpo;
    private String source;
    private List<String> responsibleEmployee;

    @OneToOne
    @JoinColumn(name = "company_id")
    private Company company;
}
