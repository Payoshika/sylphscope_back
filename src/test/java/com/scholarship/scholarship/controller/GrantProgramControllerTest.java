package com.scholarship.scholarship.controller;

import com.scholarship.scholarship.dto.ApplicationDto;
import com.scholarship.scholarship.dto.ProviderStaffDto;
import com.scholarship.scholarship.dto.QuestionDto;
import com.scholarship.scholarship.dto.QuestionGroupIdRequest;
import com.scholarship.scholarship.dto.QuestionIdRequest;
import com.scholarship.scholarship.dto.grantProgramDtos.GrantProgramDto;
import com.scholarship.scholarship.dto.grantProgramDtos.QuestionEligibilityInfoDto;
import com.scholarship.scholarship.enums.GrantStatus;
import com.scholarship.scholarship.valueObject.AssignedStaff;
import com.scholarship.scholarship.valueObject.Schedule;
import com.scholarship.scholarship.dto.grantProgramDtos.CreateGrantProgramRequestDto;
import com.scholarship.scholarship.service.ApplicationService;
import com.scholarship.scholarship.service.GrantProgramService;
import com.scholarship.scholarship.service.QuestionOptionSetService;
import com.scholarship.scholarship.service.QuestionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GrantProgramControllerTest {
    @Mock
    private GrantProgramService grantProgramService;
    @Mock
    private QuestionOptionSetService questionOptionSetService;
    @Mock
    private QuestionService questionService;
    @Mock
    private ApplicationService applicationService;
    @InjectMocks
    private GrantProgramController grantProgramController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createGrantProgram() {
        CreateGrantProgramRequestDto request = new CreateGrantProgramRequestDto();
        request.setProviderStaffId("staff1");
        GrantProgramDto grantProgramDto = new GrantProgramDto();
        request.setGrantProgramDto(grantProgramDto);
        ProviderStaffDto staffDto = new ProviderStaffDto();
        when(grantProgramService.getProviderStaffById("staff1")).thenReturn(staffDto);
        when(grantProgramService.createGrantProgram(grantProgramDto)).thenReturn(grantProgramDto);
        ResponseEntity<GrantProgramDto> response = grantProgramController.createGrantProgram(request);
        assertEquals(grantProgramDto, response.getBody());
    }

    @Test
    void getGrantProgramById() {
        GrantProgramDto dto = new GrantProgramDto();
        when(grantProgramService.getGrantProgramById("gp1")).thenReturn(dto);
        ResponseEntity<GrantProgramDto> response = grantProgramController.getGrantProgramById("gp1");
        assertEquals(dto, response.getBody());
    }

    @Test
    void searchGrantProgramByKeyword() {
        Pageable pageable = PageRequest.of(0, 10);
        GrantProgramDto dto = new GrantProgramDto();
        Page<GrantProgramDto> page = new PageImpl<>(List.of(dto), pageable, 1);
        when(grantProgramService.searchGrantProgramsByTitle("keyword", pageable)).thenReturn(page);
        ResponseEntity<Page<GrantProgramDto>> response = grantProgramController.searchGrantProgramByKeyword("keyword", pageable);
        assertEquals(page, response.getBody());
    }

    @Test
    void getAllGrantPrograms() {
        Pageable pageable = PageRequest.of(0, 10);
        GrantProgramDto dto = new GrantProgramDto();
        Page<GrantProgramDto> page = new PageImpl<>(List.of(dto), pageable, 1);
        when(grantProgramService.getAllGrantPrograms(pageable)).thenReturn(page);
        ResponseEntity<Page<GrantProgramDto>> response = grantProgramController.getAllGrantPrograms(pageable);
        assertEquals(page, response.getBody());
    }

    @Test
    void getGrantProgramsByProviderId() {
        GrantProgramDto dto = new GrantProgramDto();
        when(grantProgramService.getGrantProgramsByProviderId("provider1")).thenReturn(List.of(dto));
        ResponseEntity<List<GrantProgramDto>> response = grantProgramController.getGrantProgramsByProviderId("provider1");
        assertEquals(1, response.getBody().size());
        assertEquals(dto, response.getBody().get(0));
    }

    @Test
    void getGrantProgramByStudentId() {
        Pageable pageable = PageRequest.of(0, 10);
        GrantProgramDto dto = new GrantProgramDto();
        dto.setId("gp1");
        dto.setStatus(GrantStatus.OPEN);
        when(grantProgramService.getAllGrantPrograms()).thenReturn(List.of(dto));
        when(applicationService.getApplicationsByStudentId("student1")).thenReturn(Collections.emptyList());
        ResponseEntity<Page<GrantProgramDto>> response = grantProgramController.getGrantProgramByStudentId("student1", pageable);
        assertEquals(1, response.getBody().getContent().size());
        assertEquals(dto, response.getBody().getContent().get(0));
    }

    @Test
    void updateGrantProgram() {
        GrantProgramDto dto = new GrantProgramDto();
        when(grantProgramService.updateGrantProgram("gp1", dto)).thenReturn(dto);
        ResponseEntity<GrantProgramDto> response = grantProgramController.updateGrantProgram("gp1", dto);
        assertEquals(dto, response.getBody());
    }

    @Test
    void addQuestion() {
        GrantProgramDto dto = new GrantProgramDto();
        QuestionIdRequest request = new QuestionIdRequest();
        request.setQuestionId("q1");
        when(grantProgramService.addQuestionToGrantProgram("gp1", "q1")).thenReturn(dto);
        ResponseEntity<GrantProgramDto> response = grantProgramController.addQuestion("gp1", request);
        assertEquals(dto, response.getBody());
    }

    @Test
    void addQuestionGroup() {
        GrantProgramDto dto = new GrantProgramDto();
        QuestionGroupIdRequest request = new QuestionGroupIdRequest();
        request.setQuestionGroupId("g1");
        when(grantProgramService.addQuestionGroupToGrantProgram("gp1", "g1")).thenReturn(dto);
        ResponseEntity<GrantProgramDto> response = grantProgramController.addQuestionGroup("gp1", request);
        assertEquals(dto, response.getBody());
    }

    @Test
    void updateSchedule() {
        GrantProgramDto dto = new GrantProgramDto();
        Schedule schedule = new Schedule();
        when(grantProgramService.updateSchedule("gp1", schedule)).thenReturn(dto);
        ResponseEntity<GrantProgramDto> response = grantProgramController.updateSchedule("gp1", schedule);
        assertEquals(dto, response.getBody());
    }

    @Test
    void updateContactPerson() {
        GrantProgramDto dto = new GrantProgramDto();
        ProviderStaffDto staffDto = new ProviderStaffDto();
        when(grantProgramService.updateContactPerson("gp1", staffDto)).thenReturn(dto);
        ResponseEntity<GrantProgramDto> response = grantProgramController.updateContactPerson("gp1", staffDto);
        assertEquals(dto, response.getBody());
    }

    @Test
    void updateAssignedStaff() {
        GrantProgramDto dto = new GrantProgramDto();
        List<AssignedStaff> staffList = new ArrayList<>();
        when(grantProgramService.updateAssignedStaff("gp1", staffList)).thenReturn(dto);
        ResponseEntity<GrantProgramDto> response = grantProgramController.updateAssignedStaff("gp1", staffList);
        assertEquals(dto, response.getBody());
    }

    @Test
    void getAssignedStaff() {
        List<AssignedStaff> staffList = new ArrayList<>();
        when(grantProgramService.getAssignedStaff("gp1")).thenReturn(staffList);
        ResponseEntity<List<AssignedStaff>> response = grantProgramController.getAssignedStaff("gp1");
        assertEquals(staffList, response.getBody());
    }

    @Test
    void getContactPerson() {
        ProviderStaffDto staffDto = new ProviderStaffDto();
        when(grantProgramService.getContactPerson("gp1")).thenReturn(staffDto);
        ResponseEntity<ProviderStaffDto> response = grantProgramController.getContactPerson("gp1");
        assertEquals(staffDto, response.getBody());
    }

    @Test
    void getAppliedGrantProgram() {
        ApplicationDto appDto = new ApplicationDto();
        appDto.setGrantProgramId("gp1");
        GrantProgramDto gpDto = new GrantProgramDto();
        gpDto.setId("gp1");
        when(applicationService.getApplicationsByStudentId("student1")).thenReturn(Collections.singletonList(appDto));
        when(grantProgramService.getAllGrantPrograms()).thenReturn(Collections.singletonList(gpDto));
        ResponseEntity<List<GrantProgramDto>> response = grantProgramController.getAppliedGrantProgram("student1");
        assertEquals(1, response.getBody().size());
        assertEquals(gpDto, response.getBody().get(0));
    }

    @Test
    void searchAppliedGrantProgramByKeyword() {
        ApplicationDto appDto = new ApplicationDto();
        appDto.setGrantProgramId("gp1");
        GrantProgramDto gpDto = new GrantProgramDto();
        gpDto.setId("gp1");
        gpDto.setTitle("Scholarship for Science");
        when(applicationService.getApplicationsByStudentId("student1")).thenReturn(Collections.singletonList(appDto));
        when(grantProgramService.getAllGrantPrograms()).thenReturn(Collections.singletonList(gpDto));
        ResponseEntity<List<GrantProgramDto>> response = grantProgramController.searchAppliedGrantProgramByKeyword("student1", "science");
        assertEquals(1, response.getBody().size());
        assertEquals(gpDto, response.getBody().get(0));
    }

    @Test
    void makePublic() {
        GrantProgramDto gpDto = new GrantProgramDto();
        gpDto.setId("gp1");
        gpDto.setStatus(GrantStatus.DRAFT);
        GrantProgramDto updatedDto = new GrantProgramDto();
        updatedDto.setId("gp1");
        updatedDto.setStatus(GrantStatus.OPEN);
        when(grantProgramService.getGrantProgramById("gp1")).thenReturn(gpDto);
        when(grantProgramService.updateGrantProgram(eq("gp1"), any(GrantProgramDto.class))).thenReturn(updatedDto);
        ResponseEntity<GrantProgramDto> response = grantProgramController.makePublic("gp1");
        assertEquals(updatedDto, response.getBody());
        assertEquals(GrantStatus.OPEN, response.getBody().getStatus());
    }

    @Test
    void closeProgram() {
        GrantProgramDto gpDto = new GrantProgramDto();
        gpDto.setId("gp1");
        gpDto.setStatus(GrantStatus.OPEN);
        GrantProgramDto updatedDto = new GrantProgramDto();
        updatedDto.setId("gp1");
        updatedDto.setStatus(GrantStatus.CLOSED);
        when(grantProgramService.getGrantProgramById("gp1")).thenReturn(gpDto);
        when(grantProgramService.updateGrantProgram(eq("gp1"), any(GrantProgramDto.class))).thenReturn(updatedDto);
        ResponseEntity<GrantProgramDto> response = grantProgramController.closeProgram("gp1");
        assertEquals(updatedDto, response.getBody());
        assertEquals(GrantStatus.CLOSED, response.getBody().getStatus());
    }
}