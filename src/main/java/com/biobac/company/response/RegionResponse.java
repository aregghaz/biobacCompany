package com.biobac.company.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RegionResponse {
    private Long id;
    private String name;
    private String code;
}
