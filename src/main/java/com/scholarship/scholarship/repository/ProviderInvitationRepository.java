package com.scholarship.scholarship.repository;

import com.scholarship.scholarship.model.ProviderInvitation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProviderInvitationRepository extends MongoRepository<ProviderInvitation, String> {
    Optional<ProviderInvitation> findByToken(String token);
    Optional<ProviderInvitation> findByEmailAndProviderId(String email, String providerId);
    List<ProviderInvitation> findByProviderId(String providerId);
    List<ProviderInvitation> findByEmail(String email);
    List<ProviderInvitation> findByUsedFalseAndExpiredFalseAndExpiresAtBefore(Instant now);
    void deleteByUsedTrueOrExpiredTrue();
}

