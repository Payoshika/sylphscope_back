package com.scholarship.scholarship.service;

import com.scholarship.scholarship.dto.ApplicationDto;
import com.scholarship.scholarship.enums.ApplicationStatus;
import com.scholarship.scholarship.exception.ResourceNotFoundException;
import com.scholarship.scholarship.model.Application;
import com.scholarship.scholarship.modelmapper.ApplicationMapper;
import com.scholarship.scholarship.repository.ApplicationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ApplicationServiceTest {
    @Mock
    private ApplicationRepository applicationRepository;
    @Mock
    private ApplicationMapper applicationMapper;
    @InjectMocks
    private ApplicationService applicationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getApplicationById() {
        Application app = new Application();
        app.setId("1");
        ApplicationDto dto = new ApplicationDto();
        dto.setId("1");
        when(applicationRepository.findById("1")).thenReturn(Optional.of(app));
        when(applicationMapper.toDto(app)).thenReturn(dto);
        ApplicationDto result = applicationService.getApplicationById("1");
        assertEquals("1", result.getId());
    }

    @Test
    void getApplicationsByStudentId() {
        Application app = new Application();
        app.setId("1");
        ApplicationDto dto = new ApplicationDto();
        dto.setId("1");
        when(applicationRepository.findByStudentId("student1")).thenReturn(Arrays.asList(app));
        when(applicationMapper.toDto(app)).thenReturn(dto);
        List<ApplicationDto> result = applicationService.getApplicationsByStudentId("student1");
        assertEquals(1, result.size());
        assertEquals("1", result.get(0).getId());
    }

    @Test
    void getApplicationsByGrantProgramId() {
        Application app = new Application();
        app.setId("1");
        ApplicationDto dto = new ApplicationDto();
        dto.setId("1");
        when(applicationRepository.findByGrantProgramId("gp1")).thenReturn(Arrays.asList(app));
        when(applicationMapper.toDto(app)).thenReturn(dto);
        List<ApplicationDto> result = applicationService.getApplicationsByGrantProgramId("gp1");
        assertEquals(1, result.size());
        assertEquals("1", result.get(0).getId());
    }

    @Test
    void createApplication() {
        ApplicationDto dto = new ApplicationDto();
        Application app = new Application();
        when(applicationMapper.toEntity(dto)).thenReturn(app);
        when(applicationRepository.save(app)).thenReturn(app);
        when(applicationMapper.toDto(app)).thenReturn(dto);
        ApplicationDto result = applicationService.createApplication(dto);
        assertNotNull(result);
    }

    @Test
    void createApplicationByStudentIdAndGrantProgramId() {
        Application app = new Application();
        app.setStudentId("student1");
        app.setGrantProgramId("gp1");
        app.setStatus(ApplicationStatus.DRAFT);
        ApplicationDto dto = new ApplicationDto();
        dto.setStudentId("student1");
        dto.setGrantProgramId("gp1");
        dto.setStatus(ApplicationStatus.DRAFT);
        when(applicationRepository.save(any(Application.class))).thenReturn(app);
        when(applicationMapper.toDto(app)).thenReturn(dto);
        ApplicationDto result = applicationService.createApplicationByStudentIdAndGrantProgramId("student1", "gp1");
        assertEquals("student1", result.getStudentId());
        assertEquals("gp1", result.getGrantProgramId());
        assertEquals(ApplicationStatus.DRAFT, result.getStatus());
    }

    @Test
    void updateApplication_found() {
        ApplicationDto dto = new ApplicationDto();
        dto.setId("1");
        Application app = new Application();
        app.setId("1");
        when(applicationRepository.findById("1")).thenReturn(Optional.of(app));
        doNothing().when(applicationMapper).updateEntityFromDto(dto, app);
        when(applicationRepository.save(app)).thenReturn(app);
        when(applicationMapper.toDto(app)).thenReturn(dto);
        ApplicationDto result = applicationService.updateApplication("1", dto);
        assertEquals("1", result.getId());
    }


    @Test
    void deleteApplication_found() {
        when(applicationRepository.existsById("1")).thenReturn(true);
        doNothing().when(applicationRepository).deleteById("1");
        boolean result = applicationService.deleteApplication("1");
        assertTrue(result);
    }

    @Test
    void updateApplicationStatus_validStatus() {
        Application app = new Application();
        app.setId("1");
        ApplicationDto dto = new ApplicationDto();
        dto.setId("1");
        when(applicationRepository.findById("1")).thenReturn(Optional.of(app));
        when(applicationRepository.save(app)).thenReturn(app);
        when(applicationMapper.toDto(app)).thenReturn(dto);
        ApplicationDto result = applicationService.updateApplicationStatus("1", "DRAFT");
        assertEquals("1", result.getId());
    }

    @Test
    void updateApplicationStatus_invalidStatus() {
        Application app = new Application();
        app.setId("1");
        when(applicationRepository.findById("1")).thenReturn(Optional.of(app));
        assertThrows(RuntimeException.class, () -> applicationService.updateApplicationStatus("1", "INVALID_STATUS"));
    }

}