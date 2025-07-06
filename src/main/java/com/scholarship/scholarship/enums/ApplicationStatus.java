package com.scholarship.scholarship.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ApplicationStatus {
    DRAFT("draft"),
    APPLIED("applied"),
    UNDER_SELECTION_PROCESS("under selection process"),
    SELECTED("selected"),
    NOT_SELECTED("not selected"),
    CANCELED("canceled");

    private final String value;

    ApplicationStatus(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static ApplicationStatus fromValue(String value) {
        for (ApplicationStatus status : ApplicationStatus.values()) {
            if (status.getValue().equalsIgnoreCase(value)) {
                return status;
            }
        }
        return null;
    }
}