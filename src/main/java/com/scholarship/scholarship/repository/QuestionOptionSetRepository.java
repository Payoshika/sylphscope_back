package com.scholarship.scholarship.repository;

import com.scholarship.scholarship.model.QuestionOptionSet;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionOptionSetRepository extends MongoRepository<QuestionOptionSet, String> {
    Optional<QuestionOptionSet> findByOptionSetLabel(String optionSetLabel);
    List<QuestionOptionSet> findByOptionSetLabelIn(List<String> optionSetIds);
    List<QuestionOptionSet> findByoptionSetLabelContainingIgnoreCase(String optionSetLabel);
}