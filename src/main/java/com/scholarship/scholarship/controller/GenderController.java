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
    public ResponseEntity<List<Map<String, String>>> getAllGenders() {
        List<Map<String, String>> genders = Arrays.stream(Gender.values())
                .map(gender -> Map.of("value", gender.getValue()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(genders);
    }

    @GetMapping("/{value}")
    public ResponseEntity<Map<String, String>> getGenderByValue(@PathVariable String value) {
        Gender gender = Gender.fromValue(value);
        if (gender == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(Map.of("value", gender.getValue()));
    }
}