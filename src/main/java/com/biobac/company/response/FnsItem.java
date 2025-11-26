package com.biobac.company.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FnsItem {
    @JsonProperty("ЮЛ")
    private FnsCompany ul;
}
