package com.biobac.company.entity.embeddable;

import jakarta.persistence.Embeddable;
import lombok.*;

@Setter
@Getter
@Builder
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class Location {
    private String longitude;
    private String latitude;
}
