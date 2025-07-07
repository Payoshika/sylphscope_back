package com.scholarship.scholarship.repository;

import com.scholarship.scholarship.model.EligibilityCriteria;
import com.scholarship.scholarship.enums.EligibilityCriteriaType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EligibilityCriteriaRepository extends MongoRepository<EligibilityCriteria, String> {
    List<EligibilityCriteria> findByGrantProgramId(String grantProgramId);
    List<EligibilityCriteria> findByQuestionId(String questionId);
    List<EligibilityCriteria> findByQuestionGroupId(String questionGroupId);
    List<EligibilityCriteria> findByRequired(boolean required);
    void deleteByGrantProgramId(String grantProgramId);
}