package com.biobac.company.entity.enums;

import lombok.Getter;

// if added new element, also need to update in front end
@Getter
public enum Category {
    EMPLOYEE("Сотрдуники"),
    SELLER("Поставшики"),
    BUYER("Покупатель");

    private final String value;

    Category(String value) {
        this.value = value;
    }
}
