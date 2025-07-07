package com.scholarship.scholarship.repository;

import com.scholarship.scholarship.model.EligibilityResult;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EligibilityResultRepository extends MongoRepository<EligibilityResult, String> {
    List<EligibilityResult> findByStudentId(String studentId);
    List<EligibilityResult> findByGrantProgramId(String grantProgramId);
    Optional<EligibilityResult> findByStudentIdAndGrantProgramId(String studentId, String grantProgramId);
    void deleteByStudentId(String studentId);
    void deleteByGrantProgramId(String grantProgramId);
}