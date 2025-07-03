package com.scholarship.scholarship.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.Arrays;
import com.scholarship.scholarship.enums.Country;

@RestController
@RequestMapping("/api/countries")
public class CountryController {

    @GetMapping
    public List<Country> getAllCountries() {
        return Arrays.asList(Country.values());
    }

    @GetMapping("/search")
    public List<Country> searchCountries(@RequestParam String query) {
        return Country.searchCountries(query);
    }

    @GetMapping("/by-code/{code}")
    public ResponseEntity<Country> getByCode(@PathVariable String code) {
        Country country = Country.getByCode(code);
        return country != null ? ResponseEntity.ok(country) : ResponseEntity.notFound().build();
    }

    @GetMapping("/by-phone-code/{phoneCode}")
    public ResponseEntity<Country> getByPhoneCode(@PathVariable String phoneCode) {
        Country country = Country.getByPhoneCode(phoneCode);
        return country != null ? ResponseEntity.ok(country) : ResponseEntity.notFound().build();
    }
}