package com.biobac.company.entity.embeddable;

import jakarta.persistence.Embeddable;
import lombok.*;

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
