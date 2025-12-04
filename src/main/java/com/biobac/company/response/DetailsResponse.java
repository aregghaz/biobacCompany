package com.biobac.company.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DetailsResponse {
    private Long id;
    private String inn;
    private String kpp;
    private String ogrn;
    private String okpo;
}

