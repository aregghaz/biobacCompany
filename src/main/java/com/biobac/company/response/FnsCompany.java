package com.biobac.company.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FnsCompany {
    @JsonProperty("ИНН")
    private String inn;

    @JsonProperty("КПП")
    private String kpp;

    @JsonProperty("ОГРН")
    private String ogrn;

    @JsonProperty("НаимПолнЮЛ")
    private String fullName;

    @JsonProperty("НаимСокрЮЛ")
    private String shortName;
}