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
    void getGrantProgramById_notFound() {
        when(grantProgramRepository.findById("2")).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> grantProgramService.getGrantProgramById("2"));
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
    void getGrantProgramsByProviderIdPageable() {
        GrantProgram gp = new GrantProgram();
        gp.setId("1");
        GrantProgramDto dto = new GrantProgramDto();
        dto.setId("1");
        Pageable pageable = PageRequest.of(0, 10);
        Page<GrantProgram> page = new PageImpl<>(List.of(gp), pageable, 1);
        when(grantProgramRepository.findByProviderId("provider1", pageable)).thenReturn(page);
        when(grantProgramMapper.toDto(gp)).thenReturn(dto);
        Page<GrantProgramDto> result = grantProgramService.getGrantProgramsByProviderId("provider1", pageable);
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
    void updateGrantProgram_notFound() {
        GrantProgramDto dto = new GrantProgramDto();
        when(grantProgramRepository.existsById("2")).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> grantProgramService.updateGrantProgram("2", dto));
    }

    @Test
    void deleteGrantProgram_found() {
        when(grantProgramRepository.existsById("1")).thenReturn(true);
        doNothing().when(grantProgramRepository).deleteById("1");
        boolean result = grantProgramService.deleteGrantProgram("1");
        assertTrue(result);
    }

    @Test
    void deleteGrantProgram_notFound() {
        when(grantProgramRepository.existsById("2")).thenReturn(false);
        boolean result = grantProgramService.deleteGrantProgram("2");
        assertFalse(result);
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
    void updateSchedule_notFound() {
        Schedule schedule = new Schedule();
        when(grantProgramRepository.findById("2")).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> grantProgramService.updateSchedule("2", schedule));
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
    void addQuestionToGrantProgram_notFound() {
        when(grantProgramRepository.findById("2")).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> grantProgramService.addQuestionToGrantProgram("2", "q1"));
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
    void addQuestionGroupToGrantProgram_notFound() {
        when(grantProgramRepository.findById("2")).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> grantProgramService.addQuestionGroupToGrantProgram("2", "g1"));
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

    @Test
    void removeQuestionFromGrantProgram_notFound() {
        when(grantProgramRepository.findById("2")).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> grantProgramService.removeQuestionFromGrantProgram("2", "q1"));
    }

    @Test
    void updateContactPerson_found() {
        GrantProgram gp = new GrantProgram();
        gp.setId("1");
        ProviderStaffDto staffDto = new ProviderStaffDto();
        ProviderStaff staff = new ProviderStaff();
        when(grantProgramRepository.findById("1")).thenReturn(Optional.of(gp));
        when(grantProgramRepository.save(gp)).thenReturn(gp);
        when(grantProgramMapper.toDto(gp)).thenReturn(new GrantProgramDto());
        GrantProgramDto result = grantProgramService.updateContactPerson("1", staffDto);
        assertNotNull(result);
    }

    @Test
    void updateContactPerson_notFound() {
        ProviderStaffDto staffDto = new ProviderStaffDto();
        when(grantProgramRepository.findById("2")).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> grantProgramService.updateContactPerson("2", staffDto));
    }

    @Test
    void updateAssignedStaff_found() {
        GrantProgram gp = new GrantProgram();
        gp.setId("1");
        List<AssignedStaff> staffList = new ArrayList<>();
        when(grantProgramRepository.findById("1")).thenReturn(Optional.of(gp));
        when(grantProgramRepository.save(gp)).thenReturn(gp);
        when(grantProgramMapper.toDto(gp)).thenReturn(new GrantProgramDto());
        GrantProgramDto result = grantProgramService.updateAssignedStaff("1", staffList);
        assertNotNull(result);
    }

    @Test
    void updateAssignedStaff_notFound() {
        List<AssignedStaff> staffList = new ArrayList<>();
        when(grantProgramRepository.findById("2")).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> grantProgramService.updateAssignedStaff("2", staffList));
    }

    @Test
    void getAssignedStaff_found() {
        GrantProgram gp = new GrantProgram();
        gp.setId("1");
        List<AssignedStaff> staffList = new ArrayList<>();
        gp.setAssignedStaff(staffList);
        when(grantProgramRepository.findById("1")).thenReturn(Optional.of(gp));
        List<AssignedStaff> result = grantProgramService.getAssignedStaff("1");
        assertEquals(staffList, result);
    }

    @Test
    void getAssignedStaff_notFound() {
        when(grantProgramRepository.findById("2")).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> grantProgramService.getAssignedStaff("2"));
    }

    @Test
    void getContactPerson_found() {
        GrantProgram gp = new GrantProgram();
        gp.setId("1");
        ProviderStaff staff = new ProviderStaff();
        staff.setId("staff1");
        gp.setContactPerson(staff);
        when(grantProgramRepository.findById("1")).thenReturn(Optional.of(gp));
        ProviderStaffDto result = grantProgramService.getContactPerson("1");
        assertEquals("staff1", result.getId());
    }

    @Test
    void getContactPerson_notFound() {
        when(grantProgramRepository.findById("2")).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> grantProgramService.getContactPerson("2"));
    }

    @Test
    void getProviderStaffById_found() {
        ProviderStaff staff = new ProviderStaff();
        staff.setId("staff1");
        when(providerStaffRepository.findById("staff1")).thenReturn(Optional.of(staff));
        ProviderStaffDto result = grantProgramService.getProviderStaffById("staff1");
        assertEquals("staff1", result.getId());
    }

    @Test
    void getProviderStaffById_notFound() {
        when(providerStaffRepository.findById("staff2")).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> grantProgramService.getProviderStaffById("staff2"));
    }
}