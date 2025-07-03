package com.scholarship.scholarship.repository;

import com.scholarship.scholarship.model.Provider;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProviderRepository extends MongoRepository<Provider, String> {
    Optional<Provider> findByOrganisationName(String organisationName);
    Optional<Provider> findByContactEmail(String contactEmail);
}