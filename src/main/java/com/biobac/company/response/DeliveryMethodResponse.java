package com.biobac.company.response;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryMethodResponse {
    private Long id;
    private String name;
}
