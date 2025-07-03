package com.scholarship.scholarship.controller;

import com.scholarship.scholarship.enums.Gender;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/gender")
public class GenderController {

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getAllGenders() {
        return ResponseEntity.ok(
                Arrays.stream(Gender.values())
                        .map(Gender::toJson)
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/categories")
    public ResponseEntity<List<Map<String, String>>> getGenderCategories() {
        return ResponseEntity.ok(
                Arrays.stream(Gender.GenderCategory.values())
                        .map(category -> {
                            Map<String, String> map = Map.of(
                                    "value", category.getValue(),
                                    "label", category.getLabel()
                            );
                            return map;
                        })
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Map<String, Object>>> getGendersByCategory(@PathVariable String category) {
        Gender.GenderCategory genderCategory = Gender.GenderCategory.fromValue(category);
        if (genderCategory == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(
                Gender.getGendersByCategory(genderCategory).stream()
                        .map(Gender::toJson)
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/traditional")
    public ResponseEntity<List<Map<String, Object>>> getTraditionalGenders() {
        return ResponseEntity.ok(
                Gender.getTraditionalGenders().stream()
                        .map(Gender::toJson)
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/non-binary")
    public ResponseEntity<List<Map<String, Object>>> getNonBinaryGenders() {
        return ResponseEntity.ok(
                Gender.getNonBinaryGenders().stream()
                        .map(Gender::toJson)
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/other")
    public ResponseEntity<List<Map<String, Object>>> getOtherGenders() {
        return ResponseEntity.ok(
                Gender.getOtherGenders().stream()
                        .map(Gender::toJson)
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/search")
    public ResponseEntity<List<Map<String, Object>>> searchGenders(@RequestParam String query) {
        return ResponseEntity.ok(
                Gender.searchGenders(query).stream()
                        .map(Gender::toJson)
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/{value}")
    public ResponseEntity<Map<String, Object>> getGenderByValue(@PathVariable String value) {
        Gender gender = Gender.getByValue(value);
        if (gender == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(gender.toJson());
    }
}