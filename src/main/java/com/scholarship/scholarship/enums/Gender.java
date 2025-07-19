package com.scholarship.scholarship.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Gender {
    MALE("Male"),
    FEMALE("Female"),
    NON_BINARY("NonBinary"),
    GENDERFLUID("Genderfluid"),
    GENDERQUEER("Genderqueer"),
    AGENDER("Agender"),
    BIGENDER("Bigender"),
    DEMIGENDER("Demigender"),
    PANGENDER("Pangender"),
    TWO_SPIRIT("TwoSpirit"),
    TRANSGENDER_MALE("TransgenderMale"),
    TRANSGENDER_FEMALE("TransgenderFemale"),
    QUESTIONING("Questioning"),
    PREFER_NOT_TO_SAY("PreferNotToSay"),
    SELF_DESCRIBE("SelfDescribe"),
    OTHER("Other");

    private final String value;

    Gender(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static Gender fromValue(String value) {
        for (Gender gender : Gender.values()) {
            if (gender.value.equalsIgnoreCase(value)) {
                return gender;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return value;
    }
}