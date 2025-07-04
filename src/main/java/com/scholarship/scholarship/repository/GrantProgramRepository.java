package com.scholarship.scholarship.repository;

import com.scholarship.scholarship.model.GrantProgram;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GrantProgramRepository extends MongoRepository<GrantProgram, String> {
    List<GrantProgram> findByProviderId(String providerId);
}