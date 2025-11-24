package com.biobac.company.entity.embeddable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    private String localAddress;
    private String actualAddress;
    private String warehouseAddress;
}
