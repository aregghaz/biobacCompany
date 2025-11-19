package com.biobac.company.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EntityReferenceResponse {
    @JsonProperty("Id")
    private Long id;
    @JsonProperty("Name")
    private String name;
}