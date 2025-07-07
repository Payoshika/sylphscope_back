package com.scholarship.scholarship.repository;

import com.scholarship.scholarship.model.Question;
import com.scholarship.scholarship.enums.QuestionType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionRepository extends MongoRepository<Question, String> {
    Optional<Question> findByName(String name);
    List<Question> findByQuestionType(QuestionType type);
    List<Question> findByIsRequired(boolean required);
}