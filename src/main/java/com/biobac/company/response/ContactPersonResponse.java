package com.biobac.company.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactPersonResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String position;
}
