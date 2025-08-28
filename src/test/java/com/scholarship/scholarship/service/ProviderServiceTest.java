package com.scholarship.scholarship.service;

import com.scholarship.scholarship.dto.ProviderDto;
import com.scholarship.scholarship.dto.ProviderStaffDto;
import com.scholarship.scholarship.model.Provider;
import com.scholarship.scholarship.model.ProviderStaff;
import com.scholarship.scholarship.repository.ProviderRepository;
import com.scholarship.scholarship.repository.ProviderStaffRepository;
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

class ProviderServiceTest {
    @Mock
    private ProviderRepository providerRepository;
    @Mock
    private ProviderStaffRepository providerStaffRepository;
    @InjectMocks
    private ProviderService providerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createProvider() {
        ProviderDto dto = new ProviderDto();
        dto.setOrganisationName("Org1");
        Provider provider = new Provider();
        provider.setOrganisationName("Org1");
        when(providerRepository.save(any(Provider.class))).thenReturn(provider);
        ProviderDto result = providerService.createProvider(dto);
        assertEquals("Org1", result.getOrganisationName());
    }

    @Test
    void getProviderById_found() {
        Provider provider = new Provider();
        provider.setId("1");
        provider.setOrganisationName("Org1");
        when(providerRepository.findById("1")).thenReturn(Optional.of(provider));
        Optional<ProviderDto> result = providerService.getProviderById("1");
        assertTrue(result.isPresent());
        assertEquals("Org1", result.get().getOrganisationName());
    }

    @Test
    void updateProvider_found() {
        ProviderDto dto = new ProviderDto();
        dto.setOrganisationName("UpdatedOrg");
        Provider provider = new Provider();
        provider.setId("1");
        provider.setOrganisationName("Org1");
        when(providerRepository.findById("1")).thenReturn(Optional.of(provider));
        when(providerRepository.save(any(Provider.class))).thenReturn(provider);
        ProviderDto result = providerService.updateProvider("1", dto);
        assertEquals("UpdatedOrg", result.getOrganisationName());
    }

    @Test
    void getStaff() {
        ProviderStaff staff1 = new ProviderStaff();
        staff1.setId("staff1");
        ProviderStaff staff2 = new ProviderStaff();
        staff2.setId("staff2");
        when(providerStaffRepository.findByProviderId("1")).thenReturn(Arrays.asList(staff1, staff2));
        List<ProviderStaffDto> result = providerService.getStaff("1");
        assertEquals(2, result.size());
        assertEquals("staff1", result.get(0).getId());
        assertEquals("staff2", result.get(1).getId());
    }
}