package com.biobac.company.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public abstract class AuditableResponse {
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}