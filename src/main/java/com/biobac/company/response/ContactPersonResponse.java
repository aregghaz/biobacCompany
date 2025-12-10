package com.biobac.company.response;

import com.biobac.company.entity.enums.Category;
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
    private String notes;
    private LocalDateTime dob;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long companyId;
    private Category categoryType;
}
