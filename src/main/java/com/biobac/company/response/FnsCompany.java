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

    @JsonProperty("КодОКОПФ")
    private String okpo;

    @JsonProperty("НаимПолнЮЛ")
    private String fullName;

    @JsonProperty("НаимСокрЮЛ")
    private String shortName;

    @JsonProperty("Руководитель.ФИОПолн")
    private String seo;

    @JsonProperty("Контакты")
    private FnsContacts contacts;

    @JsonProperty("ДатаРег")
    private String dateOfRegistration;

    @JsonProperty("ДатаОГРН")
    private String dateOfOgrn;
}