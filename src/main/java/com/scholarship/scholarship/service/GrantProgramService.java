package com.scholarship.scholarship.service;

import com.scholarship.scholarship.dto.grantProgramDtos.GrantProgramDto;
import com.scholarship.scholarship.exception.ResourceNotFoundException;
import com.scholarship.scholarship.modelmapper.GrantProgramMapper;
import com.scholarship.scholarship.model.GrantProgram;
import com.scholarship.scholarship.repository.GrantProgramRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GrantProgramService {

    private final GrantProgramRepository grantProgramRepository;
    private final GrantProgramMapper grantProgramMapper;

    public List<GrantProgramDto> getAllGrantPrograms() {
        return grantProgramRepository.findAll().stream()
                .map(grantProgramMapper::toDto)
                .collect(Collectors.toList());
    }

    public Page<GrantProgramDto> getAllGrantPrograms(Pageable pageable) {
        Page<GrantProgram> entityPage = grantProgramRepository.findAll(pageable);
        List<GrantProgramDto> dtos = entityPage.getContent().stream()
                .map(grantProgramMapper::toDto)
                .collect(Collectors.toList());
        return new PageImpl<>(dtos, pageable, entityPage.getTotalElements());
    }

    public GrantProgramDto getGrantProgramById(String id) {
        return grantProgramRepository.findById(id)
                .map(grantProgramMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Grant program not found with id: " + id));
    }

    public List<GrantProgramDto> getGrantProgramsByProviderId(String providerId) {
        return grantProgramRepository.findByProviderId(providerId).stream()
                .map(grantProgramMapper::toDto)
                .collect(Collectors.toList());
    }

    public Page<GrantProgramDto> getGrantProgramsByProviderId(String providerId, Pageable pageable) {
        Page<GrantProgram> entityPage = grantProgramRepository.findByProviderId(providerId, pageable);
        List<GrantProgramDto> dtos = entityPage.getContent().stream()
                .map(grantProgramMapper::toDto)
                .collect(Collectors.toList());
        return new PageImpl<>(dtos, pageable, entityPage.getTotalElements());
    }

    public GrantProgramDto createGrantProgram(GrantProgramDto grantProgramDto) {
        GrantProgram grantProgram = grantProgramMapper.toEntity(grantProgramDto);
        GrantProgram savedEntity = grantProgramRepository.save(grantProgram);
        System.out.println(savedEntity);
        return grantProgramMapper.toDto(savedEntity);
    }

    public GrantProgramDto updateGrantProgram(String id, GrantProgramDto grantProgramDto) {
        if (!grantProgramRepository.existsById(id)) {
            throw new ResourceNotFoundException("Grant program not found with id: " + id);
        }
        GrantProgram grantProgram = grantProgramMapper.toEntity(grantProgramDto);
        grantProgram.setId(id);
        return grantProgramMapper.toDto(grantProgramRepository.save(grantProgram));
    }

    public boolean deleteGrantProgram(String id) {
        if (grantProgramRepository.existsById(id)) {
            grantProgramRepository.deleteById(id);
            return true;
        }
        return false;
    }

    //updating attributes of grant program
    // GrantProgramService.java
    public GrantProgramDto addQuestionToGrantProgram(String grantProgramId, String questionId) {
        GrantProgram grantProgram = grantProgramRepository.findById(grantProgramId)
                .orElseThrow(() -> new ResourceNotFoundException("GrantProgram not found"));
        if (grantProgram.getQuestionIds() == null) {
            grantProgram.setQuestionIds(new ArrayList<>());
        }
        if (!grantProgram.getQuestionIds().contains(questionId)) {
            grantProgram.getQuestionIds().add(questionId);
            grantProgram = grantProgramRepository.save(grantProgram);
        }
        return grantProgramMapper.toDto(grantProgram);
    }

    public GrantProgramDto addQuestionGroupToGrantProgram(String grantProgramId, String questionGroupId) {
        GrantProgram grantProgram = grantProgramRepository.findById(grantProgramId)
                .orElseThrow(() -> new ResourceNotFoundException("GrantProgram not found"));
        if (grantProgram.getQuestionGroupsIds() == null) {
            grantProgram.setQuestionGroupsIds(new ArrayList<>());
        }
        if (!grantProgram.getQuestionGroupsIds().contains(questionGroupId)) {
            grantProgram.getQuestionGroupsIds().add(questionGroupId);
            grantProgram = grantProgramRepository.save(grantProgram);
        }
        return grantProgramMapper.toDto(grantProgram);
    }
}