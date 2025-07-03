package com.scholarship.scholarship.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum StaffRole {
    MANAGER("Manager"),
    ADMINISTRATOR("Administrator"),
    ASSESSOR("Assessor"),
    VOLUNTEER("Volunteer");

    private final String value;

    StaffRole(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static StaffRole fromValue(String value) {
        for (StaffRole role : StaffRole.values()) {
            if (role.getValue().equalsIgnoreCase(value)) {
                return role;
            }
        }
        return null;
    }
}