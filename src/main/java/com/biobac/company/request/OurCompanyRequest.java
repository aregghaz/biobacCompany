package com.biobac.company.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OurCompanyRequest {
    private String name;
    private List<Long> accountIds;
}
