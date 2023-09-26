package com.foody.refrigerators.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CategoryType {
    REFRIGERATOR(0),
    CABINET(1);

    private final int categoryLevel;

}
