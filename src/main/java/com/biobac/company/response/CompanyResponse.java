package com.biobac.company.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class CompanyResponse {
    private Long id;
    private String name;
    private String address;
    private String phoneNumber;
    private String email;
    private String website;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
