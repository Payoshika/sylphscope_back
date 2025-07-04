package com.scholarship.scholarship.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum EvaluationType {
    AUTO_EVALUATE("auto_evaluate"),
    MANUAL_EVALUATE("manual_evaluate");

    private final String value;

    EvaluationType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static EvaluationType fromValue(String value) {
        for (EvaluationType type : EvaluationType.values()) {
            if (type.getValue().equalsIgnoreCase(value)) {
                return type;
            }
        }
        return null;
    }
}