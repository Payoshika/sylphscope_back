package com.scholarship.scholarship.service;

import com.scholarship.scholarship.dto.ProviderStaffDto;
import com.scholarship.scholarship.dto.grantProgramDtos.GrantProgramDto;
import com.scholarship.scholarship.exception.ResourceNotFoundException;
import com.scholarship.scholarship.model.GrantProgram;
import com.scholarship.scholarship.model.ProviderStaff;
import com.scholarship.scholarship.modelmapper.GrantProgramMapper;
import com.scholarship.scholarship.repository.GrantProgramRepository;
import com.scholarship.scholarship.repository.ProviderStaffRepository;
import com.scholarship.scholarship.valueObject.AssignedStaff;
import com.scholarship.scholarship.valueObject.Schedule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GrantProgramServiceTest {
    @Mock
    private GrantProgramRepository grantProgramRepository;
    @Mock
    private GrantProgramMapper grantProgramMapper;
    @Mock
    private ProviderStaffRepository providerStaffRepository;
    @InjectMocks
    private GrantProgramService grantProgramService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllGrantPrograms() {
        GrantProgram gp = new GrantProgram();
        gp.setId("1");
        GrantProgramDto dto = new GrantProgramDto();
        dto.setId("1");
        when(grantProgramRepository.findAll()).thenReturn(List.of(gp));
        when(grantProgramMapper.toDto(gp)).thenReturn(dto);
        List<GrantProgramDto> result = grantProgramService.getAllGrantPrograms();
        assertEquals(1, result.size());
        assertEquals("1", result.get(0).getId());
    }

    @Test
    void getAllGrantProgramsPageable() {
        GrantProgram gp = new GrantProgram();
        gp.setId("1");
        GrantProgramDto dto = new GrantProgramDto();
        dto.setId("1");
        Pageable pageable = PageRequest.of(0, 10);
        Page<GrantProgram> page = new PageImpl<>(List.of(gp), pageable, 1);
        when(grantProgramRepository.findAll(pageable)).thenReturn(page);
        when(grantProgramMapper.toDto(gp)).thenReturn(dto);
        Page<GrantProgramDto> result = grantProgramService.getAllGrantPrograms(pageable);
        assertEquals(1, result.getTotalElements());
        assertEquals("1", result.getContent().get(0).getId());
    }

    @Test
    void getGrantProgramById_found() {
        GrantProgram gp = new GrantProgram();
        gp.setId("1");
        GrantProgramDto dto = new GrantProgramDto();
        dto.setId("1");
        when(grantProgramRepository.findById("1")).thenReturn(Optional.of(gp));
        when(grantProgramMapper.toDto(gp)).thenReturn(dto);
        GrantProgramDto result = grantProgramService.getGrantProgramById("1");
        assertEquals("1", result.getId());
    }


    @Test
    void getGrantProgramsByProviderId() {
        GrantProgram gp = new GrantProgram();
        gp.setId("1");
        GrantProgramDto dto = new GrantProgramDto();
        dto.setId("1");
        when(grantProgramRepository.findByProviderId("provider1")).thenReturn(List.of(gp));
        when(grantProgramMapper.toDto(gp)).thenReturn(dto);
        List<GrantProgramDto> result = grantProgramService.getGrantProgramsByProviderId("provider1");
        assertEquals(1, result.size());
        assertEquals("1", result.get(0).getId());
    }

    @Test
    void searchGrantProgramsByTitle() {
        GrantProgram gp = new GrantProgram();
        gp.setId("1");
        GrantProgramDto dto = new GrantProgramDto();
        dto.setId("1");
        Pageable pageable = PageRequest.of(0, 10);
        Page<GrantProgram> page = new PageImpl<>(List.of(gp), pageable, 1);
        when(grantProgramRepository.findByTitleContainingIgnoreCase("keyword", pageable)).thenReturn(page);
        when(grantProgramMapper.toDto(gp)).thenReturn(dto);
        Page<GrantProgramDto> result = grantProgramService.searchGrantProgramsByTitle("keyword", pageable);
        assertEquals(1, result.getTotalElements());
        assertEquals("1", result.getContent().get(0).getId());
    }

    @Test
    void createGrantProgram() {
        GrantProgramDto dto = new GrantProgramDto();
        GrantProgram gp = new GrantProgram();
        when(grantProgramMapper.toEntity(dto)).thenReturn(gp);
        when(grantProgramRepository.save(gp)).thenReturn(gp);
        when(grantProgramMapper.toDto(gp)).thenReturn(dto);
        GrantProgramDto result = grantProgramService.createGrantProgram(dto);
        assertNotNull(result);
    }

    @Test
    void updateGrantProgram_found() {
        GrantProgramDto dto = new GrantProgramDto();
        GrantProgram gp = new GrantProgram();
        when(grantProgramRepository.existsById("1")).thenReturn(true);
        when(grantProgramMapper.toEntity(dto)).thenReturn(gp);
        when(grantProgramRepository.save(gp)).thenReturn(gp);
        when(grantProgramMapper.toDto(gp)).thenReturn(dto);
        GrantProgramDto result = grantProgramService.updateGrantProgram("1", dto);
        assertNotNull(result);
    }

    @Test
    void updateSchedule_found() {
        GrantProgram gp = new GrantProgram();
        gp.setId("1");
        Schedule schedule = new Schedule();
        gp.setSchedule(schedule);
        GrantProgramDto dto = new GrantProgramDto();
        when(grantProgramRepository.findById("1")).thenReturn(Optional.of(gp));
        when(grantProgramRepository.save(gp)).thenReturn(gp);
        when(grantProgramMapper.toDto(gp)).thenReturn(dto);
        GrantProgramDto result = grantProgramService.updateSchedule("1", schedule);
        assertNotNull(result);
    }

    @Test
    void addQuestionToGrantProgram_found() {
        GrantProgram gp = new GrantProgram();
        gp.setId("1");
        gp.setQuestionIds(new ArrayList<>());
        GrantProgramDto dto = new GrantProgramDto();
        when(grantProgramRepository.findById("1")).thenReturn(Optional.of(gp));
        when(grantProgramRepository.save(gp)).thenReturn(gp);
        when(grantProgramMapper.toDto(gp)).thenReturn(dto);
        GrantProgramDto result = grantProgramService.addQuestionToGrantProgram("1", "q1");
        assertNotNull(result);
    }

    @Test
    void addQuestionGroupToGrantProgram_found() {
        GrantProgram gp = new GrantProgram();
        gp.setId("1");
        gp.setQuestionGroupsIds(new ArrayList<>());
        GrantProgramDto dto = new GrantProgramDto();
        when(grantProgramRepository.findById("1")).thenReturn(Optional.of(gp));
        when(grantProgramRepository.save(gp)).thenReturn(gp);
        when(grantProgramMapper.toDto(gp)).thenReturn(dto);
        GrantProgramDto result = grantProgramService.addQuestionGroupToGrantProgram("1", "g1");
        assertNotNull(result);
    }

    @Test
    void removeQuestionFromGrantProgram_found() {
        GrantProgram gp = new GrantProgram();
        gp.setId("1");
        gp.setQuestionIds(new ArrayList<>(List.of("q1")));
        when(grantProgramRepository.findById("1")).thenReturn(Optional.of(gp));
        when(grantProgramRepository.save(gp)).thenReturn(gp);
        grantProgramService.removeQuestionFromGrantProgram("1", "q1");
        assertFalse(gp.getQuestionIds().contains("q1"));
    }
}