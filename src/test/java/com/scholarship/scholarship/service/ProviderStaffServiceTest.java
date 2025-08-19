// src/test/java/com/scholarship/scholarship/service/ProviderStaffServiceTest.java
package com.scholarship.scholarship.service;

import com.scholarship.scholarship.dto.ProviderStaffDto;
import com.scholarship.scholarship.enums.StaffRole;
import com.scholarship.scholarship.model.ProviderStaff;
import com.scholarship.scholarship.repository.ProviderStaffRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProviderStaffServiceTest {

    @Mock
    private ProviderStaffRepository providerStaffRepository;

    @InjectMocks
    private ProviderStaffService providerStaffService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createProviderStaff() {
        ProviderStaffDto dto = new ProviderStaffDto();
        dto.setUserId("user1");
        ProviderStaff staff = new ProviderStaff();
        staff.setUserId("user1");
        when(providerStaffRepository.save(any())).thenReturn(staff);

        ProviderStaffDto result = providerStaffService.createProviderStaff(dto);

        assertEquals("user1", result.getUserId());
        verify(providerStaffRepository, times(1)).save(any());
    }

    @Test
    void getProviderStaffById() {
        ProviderStaff staff = new ProviderStaff();
        staff.setId("id1");
        staff.setUserId("user1");
        when(providerStaffRepository.findById("id1")).thenReturn(Optional.of(staff));

        Optional<ProviderStaffDto> result = providerStaffService.getProviderStaffById("id1");

        assertTrue(result.isPresent());
        assertEquals("user1", result.get().getUserId());
    }

    @Test
    void getStaffByProviderId() {
        ProviderStaff staff = new ProviderStaff();
        staff.setProviderId("provider1");
        when(providerStaffRepository.findByProviderId("provider1")).thenReturn(List.of(staff));

        List<ProviderStaffDto> result = providerStaffService.getStaffByProviderId("provider1");

        assertEquals(1, result.size());
        assertEquals("provider1", result.get(0).getProviderId());
    }

    @Test
    void getStaffByUserIdAndProviderId() {
        ProviderStaff staff = new ProviderStaff();
        staff.setUserId("user1");
        staff.setProviderId("provider1");
        when(providerStaffRepository.findByUserIdAndProviderId("user1", "provider1")).thenReturn(Optional.of(staff));

        Optional<ProviderStaffDto> result = providerStaffService.getStaffByUserIdAndProviderId("user1", "provider1");

        assertTrue(result.isPresent());
        assertEquals("user1", result.get().getUserId());
        assertEquals("provider1", result.get().getProviderId());
    }

    @Test
    void getStaffByUserId() {
        ProviderStaff staff = new ProviderStaff();
        staff.setUserId("user1");
        when(providerStaffRepository.findByUserId("user1")).thenReturn(List.of(staff));

        List<ProviderStaffDto> result = providerStaffService.getStaffByUserId("user1");

        assertEquals(1, result.size());
        assertEquals("user1", result.get(0).getUserId());
    }

    @Test
    void getStaffByRole() {
        ProviderStaff staff = new ProviderStaff();
        staff.setRole(StaffRole.ADMINISTRATOR);
        when(providerStaffRepository.findByRole(StaffRole.ADMINISTRATOR)).thenReturn(List.of(staff));

        List<ProviderStaffDto> result = providerStaffService.getStaffByRole(StaffRole.ADMINISTRATOR);

        assertEquals(1, result.size());
        assertEquals(StaffRole.ADMINISTRATOR, result.get(0).getRole());
    }

    @Test
    void getAllProviderStaff() {
        ProviderStaff staff = new ProviderStaff();
        staff.setId("id1");
        when(providerStaffRepository.findAll()).thenReturn(List.of(staff));

        List<ProviderStaffDto> result = providerStaffService.getAllProviderStaff();

        assertEquals(1, result.size());
        assertEquals("id1", result.get(0).getId());
    }

    @Test
    void updateProviderStaff() {
        ProviderStaff staff = new ProviderStaff();
        staff.setId("id1");
        staff.setUserId("user1");
        ProviderStaffDto dto = new ProviderStaffDto();
        dto.setUserId("user2");
        when(providerStaffRepository.findById("id1")).thenReturn(Optional.of(staff));
        when(providerStaffRepository.save(any())).thenReturn(staff);

        ProviderStaffDto result = providerStaffService.updateProviderStaff("id1", dto);

        assertNotNull(result);
        verify(providerStaffRepository, times(1)).save(any());
    }

    @Test
    void deleteProviderStaff() {
        providerStaffService.deleteProviderStaff("id1");
        verify(providerStaffRepository, times(1)).deleteById("id1");
    }
}