package com.biobac.company.request;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BranchRequest {
    private String name;
    private String localAddress;
}
