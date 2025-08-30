package com.scholarship.scholarship.controller;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.scholarship.scholarship.dto.ProviderDto;
import com.scholarship.scholarship.dto.ProviderStaffDto;
import com.scholarship.scholarship.dto.StudentDto;
import com.scholarship.scholarship.dto.grantProgramDtos.GrantProgramDto;
import com.scholarship.scholarship.dto.ApplicationDto;
import com.scholarship.scholarship.service.ProviderService;
import com.scholarship.scholarship.service.GrantProgramService;
import com.scholarship.scholarship.service.ApplicationService;
import com.scholarship.scholarship.service.StudentService;
import java.util.*;
import static org.mockito.Mockito.*;
import org.springframework.http.ResponseEntity;

class ProviderControllerTest {
    @Mock
    private ProviderService providerService;
    @Mock
    private GrantProgramService grantProgramService;
    @Mock
    private ApplicationService applicationService;
    @Mock
    private StudentService studentService;
    @InjectMocks
    private ProviderController providerController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createProvider() {
        ProviderDto providerDto = new ProviderDto();
        when(providerService.createProvider(any(ProviderDto.class))).thenReturn(providerDto);
        ResponseEntity<ProviderDto> response = providerController.createProvider(providerDto);
        assertEquals(providerDto, response.getBody());
        assertEquals(201, response.getStatusCodeValue());
    }

    @Test
    void getProviderById_found() {
        ProviderDto providerDto = new ProviderDto();
        when(providerService.getProviderById("id1")).thenReturn(Optional.of(providerDto));
        ResponseEntity<ProviderDto> response = providerController.getProviderById("id1");
        assertEquals(providerDto, response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void getAllProviders() {
        List<ProviderDto> providers = List.of(new ProviderDto());
        when(providerService.getAllProviders()).thenReturn(providers);
        ResponseEntity<List<ProviderDto>> response = providerController.getAllProviders();
        assertEquals(providers, response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void getProviderByOrganisationName_found() {
        ProviderDto providerDto = new ProviderDto();
        when(providerService.getProviderByOrganisationName("org")).thenReturn(Optional.of(providerDto));
        ResponseEntity<ProviderDto> response = providerController.getProviderByOrganisationName("org");
        assertEquals(providerDto, response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void updateProvider_found() {
        ProviderDto providerDto = new ProviderDto();
        when(providerService.updateProvider(eq("id1"), any(ProviderDto.class))).thenReturn(providerDto);
        ResponseEntity<ProviderDto> response = providerController.updateProvider("id1", providerDto);
        assertEquals(providerDto, response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void deleteProvider() {
        doNothing().when(providerService).deleteProvider("id1");
        ResponseEntity<Void> response = providerController.deleteProvider("id1");
        verify(providerService, times(1)).deleteProvider("id1");
        assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    void assignContactPerson_found() {
        ProviderDto providerDto = new ProviderDto();
        ProviderStaffDto staffDto = new ProviderStaffDto();
        when(providerService.assignContactPerson("id1", staffDto)).thenReturn(providerDto);
        ResponseEntity<ProviderDto> response = providerController.assignContactPerson("id1", staffDto);
        assertEquals(providerDto, response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }
    @Test
    void getStaff() {
        List<ProviderStaffDto> staffList = List.of(new ProviderStaffDto());
        when(providerService.getStaff("id1")).thenReturn(staffList);
        ResponseEntity<List<ProviderStaffDto>> response = providerController.getStaff("id1");
        assertEquals(staffList, response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void getListOfStudentForProvider() {
        ProviderDto providerDto = new ProviderDto();
        GrantProgramDto gpDto = new GrantProgramDto();
        gpDto.setId("gp1");
        ApplicationDto appDto = new ApplicationDto();
        appDto.setStudentId("student1");
        StudentDto studentDto = new StudentDto();
        when(providerService.getProviderById("id1")).thenReturn(Optional.of(providerDto));
        when(grantProgramService.getGrantProgramsByProviderId("id1")).thenReturn(List.of(gpDto));
        when(applicationService.getApplicationsByGrantProgramId("gp1")).thenReturn(List.of(appDto));
        when(studentService.getStudentById("student1")).thenReturn(Optional.of(studentDto));
        ResponseEntity<List<Map<String, Object>>> response = providerController.getListOfStudentForProvider("id1");
        assertEquals(200, response.getStatusCodeValue());
        List<Map<String, Object>> result = response.getBody();
        assertNotNull(result);
        assertEquals(gpDto, result.get(0).get("grantProgram"));
        assertEquals(List.of(studentDto), result.get(0).get("students"));
    }

}