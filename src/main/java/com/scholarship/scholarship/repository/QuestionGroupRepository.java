package com.scholarship.scholarship.repository;

import com.scholarship.scholarship.model.QuestionGroup;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionGroupRepository extends MongoRepository<QuestionGroup, String> {
    Optional<QuestionGroup> findByName(String name);
    List<QuestionGroup> findByNameIn(List<String> names);
    List<QuestionGroup> findByNameContainingIgnoreCase(String name);
}