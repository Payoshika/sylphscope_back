package com.scholarship.scholarship.repository;

import com.scholarship.scholarship.model.SelectionCriterion;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface SelectionCriterionRepository extends MongoRepository<SelectionCriterion, String> {
    List<SelectionCriterion> findByGrantProgramId(String grantProgramId);
}