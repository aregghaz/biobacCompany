package com.biobac.company.request;

import com.biobac.company.response.AuditableResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class BranchUpdateRequest extends AuditableResponse {
    private Long id;
    private String name;
    private String localAddress;
}
