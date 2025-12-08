package com.biobac.company.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
public class SaleItem extends BaseEntity {
    @ManyToOne(optional = false)
    private Sale sale;

    private Long productId;

    private Double quantity;

    private BigDecimal unitPrice;

    private BigDecimal totalPrice;
}
