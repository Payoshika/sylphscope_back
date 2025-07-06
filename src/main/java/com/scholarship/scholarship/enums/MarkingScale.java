package com.scholarship.scholarship.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum MarkingScale {
    SCALE_100("100_scale"),
    SCALE_10("10_scale"),
    SCALE_5("5_scale");

    private final String value;

    MarkingScale(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static MarkingScale fromValue(String value) {
        for (MarkingScale scale : MarkingScale.values()) {
            if (scale.getValue().equalsIgnoreCase(value)) {
                return scale;
            }
        }
        return null;
    }
}