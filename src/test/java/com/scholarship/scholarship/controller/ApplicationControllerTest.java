package com.scholarship.scholarship.controller;

import com.scholarship.scholarship.dto.ApplicationDto;
import com.scholarship.scholarship.dto.grantProgramDtos.GrantProgramApplicationDto;
import com.scholarship.scholarship.service.ApplicationService;
import com.scholarship.scholarship.service.GrantProgramService;
import com.scholarship.scholarship.service.EvaluationOfAnswerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ApplicationControllerTest {
    @Mock
    private ApplicationService applicationService;
    @Mock
    private GrantProgramService grantProgramService;
    @Mock
    private EvaluationOfAnswerService evaluationOfAnswerService;
    @InjectMocks
    private ApplicationController applicationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllApplications() {
        ApplicationDto dto = new ApplicationDto();
        when(applicationService.getAllApplications()).thenReturn(Collections.singletonList(dto));
        ResponseEntity<List<ApplicationDto>> response = applicationController.getAllApplications();
        assertEquals(1, response.getBody().size());
    }

    @Test
    void createEmptyApplication() {
        ApplicationDto dto = new ApplicationDto();
        when(applicationService.createApplication(any(ApplicationDto.class))).thenReturn(dto);
        ResponseEntity<ApplicationDto> response = applicationController.createEmptyApplication(dto);
        assertEquals(dto, response.getBody());
    }

    @Test
    void getGrantProgramAndApplicationByStudentId() {
        ApplicationDto appDto = new ApplicationDto();
        appDto.setGrantProgramId("gp1");
        when(applicationService.getApplicationsByStudentId("student1")).thenReturn(Collections.singletonList(appDto));
        when(grantProgramService.getGrantProgramById("gp1")).thenReturn(null);
        ResponseEntity<List<GrantProgramApplicationDto>> response = applicationController.getGrantProgramAndApplicationByStudentId("student1");
        assertEquals(1, response.getBody().size());
    }

    @Test
    void getGrantProgramAndApplicationByStudentIdAndGrantProgramId_found() {
        ApplicationDto appDto = new ApplicationDto();
        appDto.setGrantProgramId("gp1");
        when(applicationService.getApplicationsByStudentId("student1")).thenReturn(Collections.singletonList(appDto));
        when(grantProgramService.getGrantProgramById("gp1")).thenReturn(null);
        ResponseEntity<GrantProgramApplicationDto> response = applicationController.getGrantProgramAndApplicationByStudentIdAndGrantProgramId("student1", "gp1");
        assertNotNull(response.getBody());
    }

    @Test
    void getApplicationById_found() {
        ApplicationDto dto = new ApplicationDto();
        when(applicationService.getApplicationById("1")).thenReturn(dto);
        ResponseEntity<ApplicationDto> response = applicationController.getApplicationById("1");
        assertEquals(dto, response.getBody());
    }

    @Test
    void getApplicationById_notFound() {
        when(applicationService.getApplicationById("2")).thenThrow(new com.scholarship.scholarship.exception.ResourceNotFoundException("Not found"));
        ResponseEntity<ApplicationDto> response = applicationController.getApplicationById("2");
        assertNull(response.getBody());
        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void getApplicationsByStudentId() {
        ApplicationDto dto = new ApplicationDto();
        when(applicationService.getApplicationsByStudentId("student1")).thenReturn(Collections.singletonList(dto));
        ResponseEntity<List<ApplicationDto>> response = applicationController.getApplicationsByStudentId("student1");
        assertEquals(1, response.getBody().size());
    }

    @Test
    void getApplicationsByGrantProgramId() {
        ApplicationDto dto = new ApplicationDto();
        when(applicationService.getApplicationsByGrantProgramId("gp1")).thenReturn(Collections.singletonList(dto));
        ResponseEntity<List<ApplicationDto>> response = applicationController.getApplicationsByGrantProgramId("gp1");
        assertEquals(1, response.getBody().size());
    }

    @Test
    void createApplication() {
        ApplicationDto dto = new ApplicationDto();
        when(applicationService.createApplication(any(ApplicationDto.class))).thenReturn(dto);
        ResponseEntity<ApplicationDto> response = applicationController.createApplication(dto);
        assertEquals(dto, response.getBody());
    }

    @Test
    void createApplicationByStudentIdAndGrantProgramId() {
        ApplicationDto dto = new ApplicationDto();
        when(applicationService.createApplicationByStudentIdAndGrantProgramId("student1", "gp1")).thenReturn(dto);
        ResponseEntity<ApplicationDto> response = applicationController.createApplicationByStudentIdAndGrantProgramId("student1", "gp1");
        assertEquals(dto, response.getBody());
    }

    @Test
    void updateApplication_found() {
        ApplicationDto dto = new ApplicationDto();
        when(applicationService.updateApplication(eq("1"), any(ApplicationDto.class))).thenReturn(dto);
        ResponseEntity<ApplicationDto> response = applicationController.updateApplication("1", dto);
        assertEquals(dto, response.getBody());
    }

    @Test
    void updateApplication_notFound() {
        when(applicationService.updateApplication(eq("2"), any(ApplicationDto.class))).thenThrow(new com.scholarship.scholarship.exception.ResourceNotFoundException("Not found"));
        ResponseEntity<ApplicationDto> response = applicationController.updateApplication("2", new ApplicationDto());
        assertNull(response.getBody());
        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void deleteApplication_found() {
        when(applicationService.deleteApplication("1")).thenReturn(true);
        ResponseEntity<Void> response = applicationController.deleteApplication("1");
        assertEquals(204, response.getStatusCode().value());
    }

    @Test
    void deleteApplication_notFound() {
        when(applicationService.deleteApplication("2")).thenReturn(false);
        ResponseEntity<Void> response = applicationController.deleteApplication("2");
        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void getApplicationCountForGrantProgram() {
        when(applicationService.getApplicationsByGrantProgramId("gp1")).thenReturn(Arrays.asList(new ApplicationDto(), new ApplicationDto()));
        ResponseEntity<Integer> response = applicationController.getApplicationCountForGrantProgram("gp1");
        assertEquals(2, response.getBody());
    }

    @Test
    void getEvaluationsByApplicationIdAndEvaluatorId() {
        List<com.scholarship.scholarship.dto.EvaluationOfAnswerDto> evals = Arrays.asList(new com.scholarship.scholarship.dto.EvaluationOfAnswerDto());
        when(evaluationOfAnswerService.getEvaluationsByApplicationIdAndEvaluatorId("app1", "eval1")).thenReturn(evals);
        ResponseEntity<List<com.scholarship.scholarship.dto.EvaluationOfAnswerDto>> response = applicationController.getEvaluationsByApplicationIdAndEvaluatorId("app1", "eval1");
        assertEquals(1, response.getBody().size());
    }

    @Test
    void addReceiver_found() {
        ApplicationDto dto = new ApplicationDto();
        when(applicationService.getApplicationById("1")).thenReturn(dto);
        when(applicationService.updateApplication(eq("1"), any(ApplicationDto.class))).thenReturn(dto);
        ResponseEntity<ApplicationDto> response = applicationController.addReceiver("1");
        assertEquals(dto, response.getBody());
    }

    @Test
    void addReceiver_notFound() {
        when(applicationService.getApplicationById("2")).thenThrow(new com.scholarship.scholarship.exception.ResourceNotFoundException("Not found"));
        ResponseEntity<ApplicationDto> response = applicationController.addReceiver("2");
        assertNull(response.getBody());
        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void updateReceivers() {
        ApplicationDto dto = new ApplicationDto();
        when(applicationService.getApplicationById(anyString())).thenReturn(dto);
        when(applicationService.updateApplication(anyString(), any(ApplicationDto.class))).thenReturn(dto);
        ResponseEntity<List<ApplicationDto>> response = applicationController.updateReceivers(Arrays.asList("1", "2"));
        assertEquals(2, response.getBody().size());
    }

    @Test
    void rejectReceiver_found() {
        ApplicationDto dto = new ApplicationDto();
        when(applicationService.getApplicationById("1")).thenReturn(dto);
        when(applicationService.updateApplication(eq("1"), any(ApplicationDto.class))).thenReturn(dto);
        ResponseEntity<ApplicationDto> response = applicationController.rejectReceiver("1");
        assertEquals(dto, response.getBody());
    }

    @Test
    void rejectReceiver_notFound() {
        when(applicationService.getApplicationById("2")).thenThrow(new com.scholarship.scholarship.exception.ResourceNotFoundException("Not found"));
        ResponseEntity<ApplicationDto> response = applicationController.rejectReceiver("2");
        assertNull(response.getBody());
        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void rejectReceivers() {
        ApplicationDto dto = new ApplicationDto();
        when(applicationService.getApplicationById(anyString())).thenReturn(dto);
        when(applicationService.updateApplication(anyString(), any(ApplicationDto.class))).thenReturn(dto);
        ResponseEntity<List<ApplicationDto>> response = applicationController.rejectReceivers(Arrays.asList("1", "2"));
        assertEquals(2, response.getBody().size());
    }

    @Test
    void applyGrant_found() {
        ApplicationDto dto = new ApplicationDto();
        when(applicationService.updateApplicationStatus("1", "APPLIED")).thenReturn(dto);
        ResponseEntity<ApplicationDto> response = applicationController.applyGrant("1");
        assertEquals(dto, response.getBody());
    }

    @Test
    void applyGrant_notFound() {
        when(applicationService.updateApplicationStatus("2", "APPLIED")).thenReturn(null);
        ResponseEntity<ApplicationDto> response = applicationController.applyGrant("2");
        assertNull(response.getBody());
        assertEquals(404, response.getStatusCode().value());
    }
}