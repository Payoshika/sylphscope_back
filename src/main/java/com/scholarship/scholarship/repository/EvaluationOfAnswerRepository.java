package com.scholarship.scholarship.repository;

import com.scholarship.scholarship.model.EvaluationOfAnswer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EvaluationOfAnswerRepository extends MongoRepository<EvaluationOfAnswer, String> {
    List<EvaluationOfAnswer> findByApplicationId(String applicationId);
    List<EvaluationOfAnswer> findByStudentAnswerId(String studentAnswerId);
    List<EvaluationOfAnswer> findByEvaluatorId(String evaluatorId);
    List<EvaluationOfAnswer> findByQuestionId(String questionId);
    List<EvaluationOfAnswer> findByQuestionGroupId(String questionGroupId);
    List<EvaluationOfAnswer> findByGrantProgramId(String grantProgramId);  // Added method for grantProgramId
    Optional<EvaluationOfAnswer> findByStudentAnswerIdAndEvaluatorId(String studentAnswerId, String evaluatorId);
    List<EvaluationOfAnswer> findByApplicationIdAndEvaluatorId(String applicationId, String evaluatorId);
}
