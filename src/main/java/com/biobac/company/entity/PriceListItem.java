package com.biobac.company.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Setter
@Getter
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class PriceListItem extends BaseEntity {
    private BigDecimal price;
    private Long productId;

    @ManyToOne
    @JoinColumn(name = "price_list_id")
    private PriceListWrapper priceListWrapper;
}
