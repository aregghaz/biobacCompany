package com.biobac.company.entity.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum Category {
    EMPLOYEE("Сотрдуники"),
    SELLER("Поставшики"),
    BUYER("Покупатель");

    private final String value;

    Category(String value) {
        this.value = value;
    }

    @JsonCreator
    public static Category fromValue(String value) {
        return Arrays.stream(Category.values())
                .filter(category -> category.getValue().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No enum constant with value: " + value));
    }

    @JsonValue
    public String value() {
        return value;
    }

}
