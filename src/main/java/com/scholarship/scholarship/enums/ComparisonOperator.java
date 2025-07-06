package com.scholarship.scholarship.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ComparisonOperator {
    EQUALS("equals"),
    NOT_EQUALS("not_equals"),
    GREATER_THAN("greater_than"),
    LESS_THAN("less_than"),
    GREATER_THAN_OR_EQUAL("greater_than_or_equal"),
    LESS_THAN_OR_EQUAL("less_than_or_equal"),
    IN_LIST("in_list"),
    NOT_IN_LIST("not_in_list"),
    EXISTS("exists"),
    NOT_EXISTS("not_exists"),
    CONTAINS("contains"),
    NOT_CONTAINS("not_contains");

    private final String value;

    ComparisonOperator(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static ComparisonOperator fromValue(String value) {
        for (ComparisonOperator condition : ComparisonOperator.values()) {
            if (condition.getValue().equalsIgnoreCase(value)) {
                return condition;
            }
        }
        return null;
    }
}