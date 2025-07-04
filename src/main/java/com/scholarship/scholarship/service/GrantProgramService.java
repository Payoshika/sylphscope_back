package com.scholarship.scholarship.service;

import com.scholarship.scholarship.model.GrantProgram;
import com.scholarship.scholarship.repository.GrantProgramRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GrantProgramService {

    private final GrantProgramRepository grantProgramRepository;

    @Autowired
    public GrantProgramService(GrantProgramRepository grantProgramRepository) {
        this.grantProgramRepository = grantProgramRepository;
    }

    public List<GrantProgram> getAllGrantPrograms() {
        return grantProgramRepository.findAll();
    }

    public Optional<GrantProgram> getGrantProgramById(String id) {
        return grantProgramRepository.findById(id);
    }

    public List<GrantProgram> getGrantProgramsByProviderId(String providerId) {
        return grantProgramRepository.findByProviderId(providerId);
    }

    public GrantProgram createGrantProgram(GrantProgram grantProgram) {
        return grantProgramRepository.save(grantProgram);
    }

    public Optional<GrantProgram> updateGrantProgram(String id, GrantProgram grantProgram) {
        if (grantProgramRepository.existsById(id)) {
            grantProgram.setId(id);
            return Optional.of(grantProgramRepository.save(grantProgram));
        }
        return Optional.empty();
    }

    public boolean deleteGrantProgram(String id) {
        if (grantProgramRepository.existsById(id)) {
            grantProgramRepository.deleteById(id);
            return true;
        }
        return false;
    }
}