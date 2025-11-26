package com.biobac.company.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FnsContacts {

    @JsonProperty("Телефон")
    private List<String> phones;

    @JsonProperty("e-mail")
    private List<String> emails;

    @JsonProperty("Сайт")
    private List<String> websites;
}

