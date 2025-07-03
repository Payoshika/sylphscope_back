package com.scholarship.scholarship.repository;

import com.scholarship.scholarship.model.PredefinedQuestionDefinition;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.List;

public interface PredefinedQuestionDefinitionRepository extends MongoRepository<PredefinedQuestionDefinition, String> {
    Optional<PredefinedQuestionDefinition> findByKey(String key);
    List<PredefinedQuestionDefinition> findByPredefinedOptionSetKey(String predefinedOptionSetKey);
    boolean existsByKey(String key);
}