package com.biobac.company.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactPersonRequest {
    private String firstName;
    private String lastName;
    private List<String> phones;
    private List<String> emails;
    private String position;
    private Long companyId;
}
