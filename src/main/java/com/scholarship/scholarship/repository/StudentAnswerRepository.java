package com.scholarship.scholarship.repository;

import com.scholarship.scholarship.model.StudentAnswer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentAnswerRepository extends MongoRepository<StudentAnswer, String> {
    List<StudentAnswer> findByStudentId(String studentId);

    Optional<StudentAnswer> findByStudentIdAndQuestionId(String studentId, String questionId);

    List<StudentAnswer> findByQuestionId(String questionId);
    List<StudentAnswer> findByStudentIdIn(List<String> studentIds);
    void deleteByStudentId(String studentId);

    void deleteByQuestionId(String questionId);
}