package com.biobac.company.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RawFnsResponse {
    private List<FnsItem> items;
}