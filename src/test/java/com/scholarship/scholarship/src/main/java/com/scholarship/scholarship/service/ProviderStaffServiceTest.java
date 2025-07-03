package com.scholarship.scholarship.service;
import com.scholarship.scholarship.dto.ProviderStaffDto;
import com.scholarship.scholarship.enums.StaffRole;
import com.scholarship.scholarship.model.ProviderStaff;
import com.scholarship.scholarship.repository.ProviderStaffRepository;
import com.scholarship.scholarship.valueObject.ProviderStaffAccessRights;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.BeanUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProviderStaffServiceTest {

    @Mock
    private ProviderStaffRepository providerStaffRepository;

    @InjectMocks
    private ProviderStaffService providerStaffService;

    private ProviderStaffDto staffDto;
    private ProviderStaff staff;
    private ProviderStaffAccessRights accessRights;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Setup test data
        accessRights = new ProviderStaffAccessRights(true, true, false, true, true, false, false);

        staffDto = new ProviderStaffDto();
        staffDto.setProviderId("provider123");
        staffDto.setUserId("user456");
        staffDto.setRole(StaffRole.ADMINISTRATOR);
        staffDto.setFirstName("John");
        staffDto.setLastName("Doe");
        staffDto.setProviderStaffAccessRights(accessRights);

        staff = new ProviderStaff();
        BeanUtils.copyProperties(staffDto, staff);
        staff.setId("generatedId");
    }

    @Test
    void createProviderStaff() {
        when(providerStaffRepository.save(any(ProviderStaff.class))).thenReturn(staff);

        ProviderStaffDto result = providerStaffService.createProviderStaff(staffDto);

        assertNotNull(result);
        assertEquals("generatedId", result.getId());
        assertEquals("John", result.getFirstName());
        assertEquals(StaffRole.ADMINISTRATOR, result.getRole());
        verify(providerStaffRepository, times(1)).save(any(ProviderStaff.class));
    }

}