package com.scholarship.scholarship.repository;
import com.scholarship.scholarship.enums.StaffRole;

import com.scholarship.scholarship.model.ProviderStaff;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProviderStaffRepository extends MongoRepository<ProviderStaff, String> {
    List<ProviderStaff> findByProviderId(String providerId);
    Optional<ProviderStaff> findByUserIdAndProviderId(String userId, String providerId);
    List<ProviderStaff> findByUserId(String userId);
    List<ProviderStaff> findByRole(StaffRole role);
}