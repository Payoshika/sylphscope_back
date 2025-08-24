package com.scholarship.scholarship.controller;

import com.scholarship.scholarship.dto.EligibilityResultDto;
import com.scholarship.scholarship.service.EligibilityResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/eligibility-result")
public class EligibilityResultController {
    @Autowired
    private EligibilityResultService eligibilityResultService;

    @GetMapping("/by-application/{applicationId}")
    public ResponseEntity<?> getEligibilityResultByApplicationId(@PathVariable String applicationId) {
        EligibilityResultDto dto = eligibilityResultService.getEligibilityResultByApplicationId(applicationId);
        if (dto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dto);
    }
}

