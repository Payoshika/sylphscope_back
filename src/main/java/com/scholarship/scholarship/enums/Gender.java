package com.scholarship.scholarship.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;

@Getter
public enum Gender {
    // Traditional
    MALE("male", "Male", GenderCategory.TRADITIONAL),
    FEMALE("female", "Female", GenderCategory.TRADITIONAL),

    // Non-Binary and Gender Diverse
    NON_BINARY("non-binary", "Non-Binary", GenderCategory.NON_BINARY),
    GENDERFLUID("genderfluid", "Gender Fluid", GenderCategory.NON_BINARY),
    GENDERQUEER("genderqueer", "Genderqueer", GenderCategory.NON_BINARY),
    AGENDER("agender", "Agender", GenderCategory.NON_BINARY),
    BIGENDER("bigender", "Bigender", GenderCategory.NON_BINARY),
    DEMIGENDER("demigender", "Demigender", GenderCategory.NON_BINARY),
    PANGENDER("pangender", "Pangender", GenderCategory.NON_BINARY),
    TWO_SPIRIT("two-spirit", "Two-Spirit", GenderCategory.NON_BINARY),

    // Other
    TRANSGENDER_MALE("transgender-male", "Transgender Male", GenderCategory.OTHER),
    TRANSGENDER_FEMALE("transgender-female", "Transgender Female", GenderCategory.OTHER),
    QUESTIONING("questioning", "Questioning", GenderCategory.OTHER),
    PREFER_NOT_TO_SAY("prefer-not-to-say", "Prefer Not to Say", GenderCategory.OTHER),
    SELF_DESCRIBE("self-describe", "Self-Describe", GenderCategory.OTHER),
    OTHER("other", "Other", GenderCategory.OTHER);

    public enum GenderCategory {
        TRADITIONAL("traditional", "Traditional"),
        NON_BINARY("non-binary", "Non-Binary & Gender Diverse"),
        OTHER("other", "Other");

        private final String value;
        private final String label;

        GenderCategory(String value, String label) {
            this.value = value;
            this.label = label;
        }

        public String getValue() {
            return value;
        }

        public String getLabel() {
            return label;
        }

        public static GenderCategory fromValue(String value) {
            for (GenderCategory category : GenderCategory.values()) {
                if (category.getValue().equals(value)) {
                    return category;
                }
            }
            return null;
        }

        @Override
        public String toString() {
            return label;
        }
    }

    private final String value;
    private final String label;
    private final GenderCategory category;

    Gender(String value, String label, GenderCategory category) {
        this.value = value;
        this.label = label;
        this.category = category;
    }

    // Static helper methods
    public static Gender getByValue(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }

        for (Gender gender : Gender.values()) {
            if (gender.getValue().equalsIgnoreCase(value)) {
                return gender;
            }
        }
        return null;
    }

    public static String getGenderLabel(String value) {
        Gender gender = getByValue(value);
        return gender != null ? gender.getLabel() : "";
    }

    public static List<Gender> getGendersByCategory(GenderCategory category) {
        return Arrays.stream(Gender.values())
                .filter(gender -> gender.getCategory() == category)
                .collect(Collectors.toList());
    }

    public static List<Gender> searchGenders(String query) {
        if (query == null || query.trim().isEmpty()) {
            return Arrays.asList(Gender.values());
        }

        String searchTerm = query.toLowerCase().trim();
        return Arrays.stream(Gender.values())
                .filter(gender ->
                        gender.getLabel().toLowerCase().contains(searchTerm) ||
                                gender.getValue().toLowerCase().contains(searchTerm)
                )
                .collect(Collectors.toList());
    }

    // Get all traditional genders
    public static List<Gender> getTraditionalGenders() {
        return getGendersByCategory(GenderCategory.TRADITIONAL);
    }

    // Get all non-binary genders
    public static List<Gender> getNonBinaryGenders() {
        return getGendersByCategory(GenderCategory.NON_BINARY);
    }

    // Get all other genders
    public static List<Gender> getOtherGenders() {
        return getGendersByCategory(GenderCategory.OTHER);
    }

    // Check if gender is traditional
    public boolean isTraditional() {
        return this.category == GenderCategory.TRADITIONAL;
    }

    // Check if gender is non-binary
    public boolean isNonBinary() {
        return this.category == GenderCategory.NON_BINARY;
    }

    // Check if gender is other category
    public boolean isOther() {
        return this.category == GenderCategory.OTHER;
    }

    // For JSON serialization (useful for REST APIs)
    @JsonValue
    public Map<String, Object> toJson() {
        Map<String, Object> map = new HashMap<>();
        map.put("value", this.value);
        map.put("label", this.label);
        map.put("category", this.category.getValue());
        return map;
    }

    // For creating from JSON
    @JsonCreator
    public static Gender fromJson(String value) {
        return getByValue(value);
    }

    @Override
    public String toString() {
        return String.format("Gender{value='%s', label='%s', category='%s'}",
                value, label, category.getValue());
    }
}