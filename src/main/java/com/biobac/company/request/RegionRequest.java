package com.biobac.company.request;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegionRequest {
    private String name;
    private String code;
}
