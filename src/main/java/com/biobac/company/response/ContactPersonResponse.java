package com.biobac.company.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactPersonResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private List<String> phones;
    private List<String> emails;
    private String position;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
