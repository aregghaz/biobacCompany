package com.biobac.company.entity;

import com.biobac.company.entity.enums.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentCategory extends BaseEntity {
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private PaymentCategory parent;

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    private List<PaymentCategory> children = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Category category;
}
