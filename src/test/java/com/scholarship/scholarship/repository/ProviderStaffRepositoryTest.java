package com.scholarship.scholarship.repository;

import com.scholarship.scholarship.enums.StaffRole;
import com.scholarship.scholarship.model.ProviderStaff;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProviderStaffRepositoryTest {
    @Mock
    private ProviderStaffRepository providerStaffRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findByProviderId() {
        ProviderStaff staff1 = new ProviderStaff();
        staff1.setId("1");
        staff1.setProviderId("provider1");
        ProviderStaff staff2 = new ProviderStaff();
        staff2.setId("2");
        staff2.setProviderId("provider1");
        when(providerStaffRepository.findByProviderId("provider1")).thenReturn(Arrays.asList(staff1, staff2));
        List<ProviderStaff> result = providerStaffRepository.findByProviderId("provider1");
        assertEquals(2, result.size());
        assertEquals("provider1", result.get(0).getProviderId());
    }

    @Test
    void findByUserId() {
        ProviderStaff staff1 = new ProviderStaff();
        staff1.setId("1");
        staff1.setUserId("user1");
        ProviderStaff staff2 = new ProviderStaff();
        staff2.setId("2");
        staff2.setUserId("user1");
        when(providerStaffRepository.findByUserId("user1")).thenReturn(Arrays.asList(staff1, staff2));
        List<ProviderStaff> result = providerStaffRepository.findByUserId("user1");
        assertEquals(2, result.size());
        assertEquals("user1", result.get(0).getUserId());
    }

}