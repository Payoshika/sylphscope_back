// src/main/java/com/scholarship/scholarship/repository/PredefinedQuestionGroupRepository.java
package com.scholarship.scholarship.repository;

import com.scholarship.scholarship.model.PredefinedQuestionGroup;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PredefinedQuestionGroupRepository extends MongoRepository<PredefinedQuestionGroup, String> {
    Optional<PredefinedQuestionGroup> findByKey(String key);
}