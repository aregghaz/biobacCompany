package com.biobac.company.entity;

import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class SaleStatus extends BaseEntity{
    private String name;
}
