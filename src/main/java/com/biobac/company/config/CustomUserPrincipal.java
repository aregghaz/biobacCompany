package com.biobac.company.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CustomUserPrincipal {
    private Long userId;
    private String username;
}
