package com.biobac.company.response;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SourceResponse {
    private Long id;
    private String name;
}
