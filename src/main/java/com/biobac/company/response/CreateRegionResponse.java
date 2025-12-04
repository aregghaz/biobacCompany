package com.biobac.company.response;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateRegionResponse {
    private Long id;
    private String regionName;
    private String code;
}
