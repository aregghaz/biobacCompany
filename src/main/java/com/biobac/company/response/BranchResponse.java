package com.biobac.company.response;

import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BranchResponse {
    private Long id;
    private String name;
    private String localAddress;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
