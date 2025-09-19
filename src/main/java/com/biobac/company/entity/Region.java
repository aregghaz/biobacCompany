package com.biobac.company.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Region {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "region_id", insertable = false, updatable = false)
    private Integer id;
    @Column(name = "region_name")
    private String name;
    @Column(name = "region_code")
    private String code;

    public Region(String name, String code) {
        this.name = name;
        this.code = code;
    }
}
