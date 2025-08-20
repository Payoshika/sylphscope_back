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
    void getAllApplications() {
        Application app1 = new Application();
        app1.setId("1");
        Application app2 = new Application();
        app2.setId("2");
        ApplicationDto dto1 = new ApplicationDto();
        dto1.setId("1");
        ApplicationDto dto2 = new ApplicationDto();
        dto2.setId("2");
        when(applicationRepository.findAll()).thenReturn(Arrays.asList(app1, app2));
        when(applicationMapper.toDto(app1)).thenReturn(dto1);
        when(applicationMapper.toDto(app2)).thenReturn(dto2);
        List<ApplicationDto> result = applicationService.getAllApplications();
        assertEquals(2, result.size());
        assertEquals("1", result.get(0).getId());
        assertEquals("2", result.get(1).getId());
    }

    @Test
    void getApplicationById_found() {
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
    void getApplicationById_notFound() {
        when(applicationRepository.findById("2")).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> applicationService.getApplicationById("2"));
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
    void updateApplication_notFound() {
        ApplicationDto dto = new ApplicationDto();
        when(applicationRepository.findById("2")).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> applicationService.updateApplication("2", dto));
    }

    @Test
    void deleteApplication_found() {
        when(applicationRepository.existsById("1")).thenReturn(true);
        doNothing().when(applicationRepository).deleteById("1");
        boolean result = applicationService.deleteApplication("1");
        assertTrue(result);
    }

    @Test
    void deleteApplication_notFound() {
        when(applicationRepository.existsById("2")).thenReturn(false);
        boolean result = applicationService.deleteApplication("2");
        assertFalse(result);
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

    @Test
    void updateApplicationStatus_notFound() {
        when(applicationRepository.findById("2")).thenReturn(Optional.empty());
        ApplicationDto result = applicationService.updateApplicationStatus("2", "DRAFT");
        assertNull(result);
    }
}