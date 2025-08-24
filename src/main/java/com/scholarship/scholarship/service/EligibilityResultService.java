package com.scholarship.scholarship.service;

import com.scholarship.scholarship.dto.EligibilityResultDto;
import com.scholarship.scholarship.model.EligibilityResult;
import com.scholarship.scholarship.repository.EligibilityResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EligibilityResultService {
    @Autowired
    private EligibilityResultRepository eligibilityResultRepository;

    public EligibilityResultDto getEligibilityResultByApplicationId(String applicationId) {
        Optional<EligibilityResult> resultOpt = eligibilityResultRepository.findByApplicationId(applicationId);
        if (resultOpt.isEmpty()) {
            return null;
        }
        EligibilityResult result = resultOpt.get();
        EligibilityResultDto dto = new EligibilityResultDto();
        dto.setId(result.getId());
        dto.setApplicationId(result.getApplicationId());
        dto.setStudentId(result.getStudentId());
        dto.setGrantProgramId(result.getGrantProgramId());
        dto.setEligible(result.isEligible());
        dto.setPassedCriteria(result.getPassedCriteria());
        dto.setFailedCriteria(result.getFailedCriteria());
        dto.setEvaluatedAt(result.getEvaluatedAt());
        dto.setUpdatedAt(result.getUpdatedAt());
        return dto;
    }
}

