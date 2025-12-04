package com.biobac.company.request;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateRegionRequest {
    private String regionName;
    private String code;
}
