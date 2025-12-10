package com.biobac.company.request;

import com.biobac.company.entity.enums.Category;
import lombok.*;

import java.time.LocalDateTime;
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
    private String notes;
    private LocalDateTime dob;
    private Category categoryType;
}
