package com.app.auth.constants;

import lombok.Getter;
import lombok.ToString;


@Getter
@ToString
public enum SortOrder {
    ASC("asc"),
    DESC("desc");

    private final String value;

    SortOrder(String value) {
        this.value = value;
    }
}
