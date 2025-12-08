package com.biobac.company.entity;

import com.biobac.company.entity.enums.Category;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PaymentCategory extends BaseEntity {
    private String name;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private PaymentCategory parent;

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PaymentCategory> children = new ArrayList<>();

    @Column(length = 30)
    @Enumerated(EnumType.STRING)
    private Category category;

    public void addChild(PaymentCategory child) {
        children.add(child);
        child.setParent(this);
    }

    public void removeChild(PaymentCategory child) {
        children.remove(child);
        child.setParent(null);
    }

}
