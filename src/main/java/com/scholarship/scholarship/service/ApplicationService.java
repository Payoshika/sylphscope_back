package com.scholarship.scholarship.service;

import com.scholarship.scholarship.dto.ApplicationDto;
import com.scholarship.scholarship.exception.ResourceNotFoundException;
import com.scholarship.scholarship.model.Application;
import com.scholarship.scholarship.modelmapper.ApplicationMapper;
import com.scholarship.scholarship.repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final ApplicationMapper applicationMapper;

    @Autowired
    public ApplicationService(ApplicationRepository applicationRepository, ApplicationMapper applicationMapper) {
        this.applicationRepository = applicationRepository;
        this.applicationMapper = applicationMapper;
    }

    public List<ApplicationDto> getAllApplications() {
        return applicationRepository.findAll().stream()
                .map(applicationMapper::toDto)
                .collect(Collectors.toList());
    }

    public ApplicationDto getApplicationById(String id) {
        return applicationRepository.findById(id)
                .map(applicationMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found with id: " + id));
    }

    public List<ApplicationDto> getApplicationsByStudentId(String studentId) {
        return applicationRepository.findByStudentId(studentId).stream()
                .map(applicationMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<ApplicationDto> getApplicationsByGrantProgramId(String grantProgramId) {
        return applicationRepository.findByGrantProgramId(grantProgramId).stream()
                .map(applicationMapper::toDto)
                .collect(Collectors.toList());
    }

    public ApplicationDto createApplication(ApplicationDto applicationDto) {
        Application application = applicationMapper.toEntity(applicationDto);
        Application savedApplication = applicationRepository.save(application);
        return applicationMapper.toDto(savedApplication);
    }

    public ApplicationDto updateApplication(String id, ApplicationDto applicationDto) {
        return applicationRepository.findById(id)
                .map(existingApplication -> {
                    applicationDto.setId(id);
                    applicationMapper.updateEntityFromDto(applicationDto, existingApplication);
                    return applicationMapper.toDto(applicationRepository.save(existingApplication));
                })
                .orElseThrow(() -> new ResourceNotFoundException("Application not found with id: " + id));
    }

    public boolean deleteApplication(String id) {
        if (!applicationRepository.existsById(id)) {
            return false;
        }
        applicationRepository.deleteById(id);
        return true;
    }
}