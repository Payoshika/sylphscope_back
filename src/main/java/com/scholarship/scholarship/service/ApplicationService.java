package com.scholarship.scholarship.service;

import com.scholarship.scholarship.model.Application;
import com.scholarship.scholarship.repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;

    @Autowired
    public ApplicationService(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    public List<Application> getAllApplications() {
        return applicationRepository.findAll();
    }

    public Optional<Application> getApplicationById(String id) {
        return applicationRepository.findById(id);
    }

    public List<Application> getApplicationsByStudentId(String studentId) {
        return applicationRepository.findByStudentId(studentId);
    }

    public List<Application> getApplicationsByGrantProgramId(String grantProgramId) {
        return applicationRepository.findByGrantProgramId(grantProgramId);
    }

    public Application createApplication(Application application) {
        return applicationRepository.save(application);
    }

    public Optional<Application> updateApplication(String id, Application application) {
        if (applicationRepository.existsById(id)) {
            application.setId(id);
            return Optional.of(applicationRepository.save(application));
        }
        return Optional.empty();
    }

    public boolean deleteApplication(String id) {
        if (applicationRepository.existsById(id)) {
            applicationRepository.deleteById(id);
            return true;
        }
        return false;
    }
}