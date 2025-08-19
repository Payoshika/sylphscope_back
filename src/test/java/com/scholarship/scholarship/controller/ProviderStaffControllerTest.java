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
    private ApplicationService applicationService;
    @Mock
    private StudentService studentService;
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
    void getProviderStaffById_found() {
        ProviderStaffDto dto = new ProviderStaffDto();
        when(providerStaffService.getProviderStaffById("id1")).thenReturn(Optional.of(dto));
        ResponseEntity<ProviderStaffDto> response = providerStaffController.getProviderStaffById("id1");
        assertEquals(dto, response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void getProviderStaffById_notFound() {
        when(providerStaffService.getProviderStaffById("id1")).thenReturn(Optional.empty());
        ResponseEntity<ProviderStaffDto> response = providerStaffController.getProviderStaffById("id1");
        assertNull(response.getBody());
        assertEquals(404, response.getStatusCodeValue());
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
    void getStaffByUserIdAndProviderId_found() {
        ProviderStaffDto dto = new ProviderStaffDto();
        when(providerStaffService.getStaffByUserIdAndProviderId("uid", "pid")).thenReturn(Optional.of(dto));
        ResponseEntity<ProviderStaffDto> response = providerStaffController.getStaffByUserIdAndProviderId("uid", "pid");
        assertEquals(dto, response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void getStaffByUserIdAndProviderId_notFound() {
        when(providerStaffService.getStaffByUserIdAndProviderId("uid", "pid")).thenReturn(Optional.empty());
        ResponseEntity<ProviderStaffDto> response = providerStaffController.getStaffByUserIdAndProviderId("uid", "pid");
        assertNull(response.getBody());
        assertEquals(404, response.getStatusCodeValue());
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
    void getStaffByUserId_notFound() {
        when(providerStaffService.getStaffByUserId("uid")).thenReturn(List.of());
        ResponseEntity<ProviderStaffDto> response = providerStaffController.getStaffByUserId("uid");
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void getStaffByRole_valid() {
        ProviderStaffDto dto = new ProviderStaffDto();
        when(providerStaffService.getStaffByRole(StaffRole.MANAGER)).thenReturn(List.of(dto));
        ResponseEntity<List<ProviderStaffDto>> response = providerStaffController.getStaffByRole("MANAGER");
        assertEquals(List.of(dto), response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void getStaffByRole_invalid() {
        ResponseEntity<List<ProviderStaffDto>> response = providerStaffController.getStaffByRole("INVALID_ROLE");
        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    void getAllProviderStaff() {
        List<ProviderStaffDto> staffList = List.of(new ProviderStaffDto());
        when(providerStaffService.getAllProviderStaff()).thenReturn(staffList);
        ResponseEntity<List<ProviderStaffDto>> response = providerStaffController.getAllProviderStaff();
        assertEquals(staffList, response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void getAllRoles() {
        ResponseEntity<List<Map<String, String>>> response = providerStaffController.getAllRoles();
        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().isEmpty());
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
    void removeStaff_notFound() {
        when(providerStaffService.getProviderStaffById("sid")).thenReturn(Optional.empty());
        ResponseEntity<ProviderStaffDto> response = providerStaffController.removeStaff("sid");
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void updateProviderStaff_found() {
        ProviderStaffDto dto = new ProviderStaffDto();
        when(providerStaffService.updateProviderStaff(eq("sid"), any(ProviderStaffDto.class))).thenReturn(dto);
        ResponseEntity<ProviderStaffDto> response = providerStaffController.updateProviderStaff("sid", dto);
        assertEquals(dto, response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void updateProviderStaff_notFound() {
        when(providerStaffService.updateProviderStaff(eq("sid"), any(ProviderStaffDto.class))).thenReturn(null);
        ResponseEntity<ProviderStaffDto> response = providerStaffController.updateProviderStaff("sid", new ProviderStaffDto());
        assertNull(response.getBody());
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void deleteProviderStaff() {
        doNothing().when(providerStaffService).deleteProviderStaff("sid");
        ResponseEntity<Void> response = providerStaffController.deleteProviderStaff("sid");
        verify(providerStaffService, times(1)).deleteProviderStaff("sid");
        assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    void inviteProviderStaff_success() {
        InviteProviderStaffRequest req = new InviteProviderStaffRequest();
        ProviderInvitation invitation = new ProviderInvitation();
        invitation.setToken("token123");
        when(providerInvitationService.createInvitation(any(), any())).thenReturn(invitation);
        ResponseEntity<Map<String, String>> response = providerStaffController.inviteProviderStaff(req, "Bearer token");
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().containsKey("invitationUrl"));
    }

    @Test
    void inviteProviderStaff_error() {
        InviteProviderStaffRequest req = new InviteProviderStaffRequest();
        when(providerInvitationService.createInvitation(any(), any())).thenThrow(new RuntimeException("fail"));
        ResponseEntity<Map<String, String>> response = providerStaffController.inviteProviderStaff(req, "Bearer token");
        assertEquals(400, response.getStatusCodeValue());
        assertTrue(response.getBody().containsKey("error"));
    }

    @Test
    void getInvitationDetails_success() {
        ProviderInvitation invitation = new ProviderInvitation();
        invitation.setEmail("test@test.com");
        invitation.setFirstName("John");
        invitation.setLastName("Doe");
        invitation.setRole(StaffRole.MANAGER);
        invitation.setProviderId("pid");
        invitation.setExpiresAt(java.time.Instant.now().plusSeconds(3600));
        invitation.setUsed(false);
        when(providerInvitationService.getInvitationByToken("token123")).thenReturn(invitation);
        ProviderDto provider = new ProviderDto();
        provider.setOrganisationName("Org");
        when(providerService.getProviderById("pid")).thenReturn(Optional.of(provider));
        ResponseEntity<Map<String, Object>> response = providerStaffController.getInvitationDetails("token123");
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("John", response.getBody().get("firstName"));
        assertEquals("Org", response.getBody().get("providerName"));
    }

    @Test
    void getInvitationDetails_notFound() {
        when(providerInvitationService.getInvitationByToken("token123")).thenThrow(new RuntimeException());
        ResponseEntity<Map<String, Object>> response = providerStaffController.getInvitationDetails("token123");
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void acceptInvitation_success() {
        AcceptInvitationRequest req = new AcceptInvitationRequest();
        ProviderStaffDto staff = new ProviderStaffDto();
        when(providerInvitationService.acceptInvitation(any())).thenReturn(staff);
        ResponseEntity<Map<String, Object>> response = providerStaffController.acceptInvitation(req);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(staff, response.getBody().get("providerStaff"));
    }

    @Test
    void acceptInvitation_error() {
        AcceptInvitationRequest req = new AcceptInvitationRequest();
        when(providerInvitationService.acceptInvitation(any())).thenThrow(new RuntimeException("fail"));
        ResponseEntity<Map<String, Object>> response = providerStaffController.acceptInvitation(req);
        assertEquals(400, response.getStatusCodeValue());
        assertTrue(response.getBody().containsKey("error"));
    }

    @Test
    void getPendingInvitations() {
        List<ProviderInvitation> invitations = List.of(new ProviderInvitation());
        when(providerInvitationService.getPendingInvitationsByProvider("pid")).thenReturn(invitations);
        ResponseEntity<List<ProviderInvitation>> response = providerStaffController.getPendingInvitations("pid");
        assertEquals(invitations, response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void cancelInvitation_success() {
        when(providerInvitationService.cancelInvitation("token123", "current-user-id")).thenReturn(true);
        ResponseEntity<Map<String, String>> response = providerStaffController.cancelInvitation("token123", "Bearer token");
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Invitation cancelled successfully", response.getBody().get("message"));
    }

    @Test
    void cancelInvitation_error() {
        when(providerInvitationService.cancelInvitation("token123", "current-user-id")).thenReturn(false);
        ResponseEntity<Map<String, String>> response = providerStaffController.cancelInvitation("token123", "Bearer token");
        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Unable to cancel invitation", response.getBody().get("error"));
    }

    @Test
    void cancelInvitation_exception() {
        when(providerInvitationService.cancelInvitation("token123", "current-user-id")).thenThrow(new RuntimeException("fail"));
        ResponseEntity<Map<String, String>> response = providerStaffController.cancelInvitation("token123", "Bearer token");
        assertEquals(400, response.getStatusCodeValue());
        assertTrue(response.getBody().containsKey("error"));
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