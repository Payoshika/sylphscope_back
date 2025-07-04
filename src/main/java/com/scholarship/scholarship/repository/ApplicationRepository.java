package com.scholarship.scholarship.repository;

import com.scholarship.scholarship.model.Application;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends MongoRepository<Application, String> {
    List<Application> findByStudentId(String studentId);
    List<Application> findByGrantProgramId(String grantProgramId);
}