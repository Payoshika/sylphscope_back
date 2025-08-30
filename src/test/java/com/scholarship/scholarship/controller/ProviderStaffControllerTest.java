package com.scholarship.scholarship.controller;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.scholarship.scholarship.dto.*;
import com.scholarship.scholarship.enums.StaffRole;
import com.scholarship.scholarship.model.ProviderInvitation;
import com.scholarship.scholarship.service.*;
import com.scholarship.scholarship.dto.grantProgramDtos.GrantProgramDto;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class ProviderStaffControllerTest {
    @Mock
    private ProviderStaffService providerStaffService;
    @Mock
    private GrantProgramService grantProgramService;
    @Mock
    private ProviderService providerService;
    @Mock
    private ProviderInvitationService providerInvitationService;
    @InjectMocks
    private ProviderStaffController providerStaffController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createProviderStaff() {
        ProviderStaffDto dto = new ProviderStaffDto();
        when(providerStaffService.createProviderStaff(any(ProviderStaffDto.class))).thenReturn(dto);
        ResponseEntity<ProviderStaffDto> response = providerStaffController.createProviderStaff(dto);
        assertEquals(dto, response.getBody());
        assertEquals(201, response.getStatusCodeValue());
    }

    @Test
    void getStaffByProviderId() {
        List<ProviderStaffDto> staffList = List.of(new ProviderStaffDto());
        when(providerStaffService.getStaffByProviderId("pid")).thenReturn(staffList);
        ResponseEntity<List<ProviderStaffDto>> response = providerStaffController.getStaffByProviderId("pid");
        assertEquals(staffList, response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void getStaffByUserId_found() {
        ProviderStaffDto dto = new ProviderStaffDto();
        when(providerStaffService.getStaffByUserId("uid")).thenReturn(List.of(dto));
        ResponseEntity<ProviderStaffDto> response = providerStaffController.getStaffByUserId("uid");
        assertEquals(dto, response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void removeStaff_success() {
        ProviderStaffDto staff = new ProviderStaffDto();
        staff.setProviderId("pid");
        when(providerStaffService.getProviderStaffById("sid")).thenReturn(Optional.of(staff));
        when(grantProgramService.getGrantProgramsByProviderId("pid")).thenReturn(List.of());
        when(providerStaffService.updateProviderStaff(eq("sid"), any(ProviderStaffDto.class))).thenReturn(staff);
        ResponseEntity<ProviderStaffDto> response = providerStaffController.removeStaff("sid");
        assertEquals(staff, response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void updateStaffProfile() {
        ProviderStaffDto staff = new ProviderStaffDto();
        when(providerStaffService.updateProviderStaff(eq(staff.getId()), any(ProviderStaffDto.class))).thenReturn(staff);
        ResponseEntity<ProviderStaffDto> response = providerStaffController.updateStaffProfile(staff);
        assertEquals(staff, response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }
}