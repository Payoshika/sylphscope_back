package com.scholarship.scholarship.repository;

import com.scholarship.scholarship.model.Option;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OptionRepository extends MongoRepository<Option, String> {
    Optional<Option> findByLabel(String label);
    List<Option> findByValue(Object value);
    List<Option> findByLabelContainingIgnoreCase(String label);
}