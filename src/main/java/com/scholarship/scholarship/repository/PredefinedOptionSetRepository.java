package com.scholarship.scholarship.repository;

import com.scholarship.scholarship.model.PredefinedOptionSet;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PredefinedOptionSetRepository extends MongoRepository<PredefinedOptionSet, String> {
    Optional<PredefinedOptionSet> findByKey(String key);
    boolean existsByKey(String key);
}