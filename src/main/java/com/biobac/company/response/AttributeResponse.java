package com.biobac.company.response;

import com.biobac.company.entity.enums.AttributeDataType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttributeResponse {
    private Long id;
    private String name;
    private AttributeDataType dataType;
    private Object value;
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;
}
