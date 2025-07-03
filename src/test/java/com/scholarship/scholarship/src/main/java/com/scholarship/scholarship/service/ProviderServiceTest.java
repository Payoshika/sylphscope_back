package com.scholarship.scholarship.service;
import com.scholarship.scholarship.dto.ProviderDto;
import com.scholarship.scholarship.model.Provider;
import com.scholarship.scholarship.repository.ProviderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.BeanUtils;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProviderServiceTest {

    @Mock
    private ProviderRepository providerRepository;

    @InjectMocks
    private ProviderService providerService;

    private ProviderDto providerDto;
    private Provider provider;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Setup test data
        providerDto = new ProviderDto();
        providerDto.setOrganisationName("Test University");
        providerDto.setContactEmail("contact@testuniversity.edu");
        providerDto.setContactPhone("+1234567890");
        providerDto.setWebsiteUrl("https://testuniversity.edu");
        providerDto.setOrganisationDescription("A leading educational institution");

        provider = new Provider();
        BeanUtils.copyProperties(providerDto, provider);
        provider.setId("generatedId");
        provider.setCreatedAt(Instant.now());
    }

    @Test
    void createProvider() {
        when(providerRepository.save(any(Provider.class))).thenReturn(provider);

        ProviderDto result = providerService.createProvider(providerDto);

        assertNotNull(result);
        assertEquals("generatedId", result.getId());
        assertEquals("Test University", result.getOrganisationName());
        assertEquals("contact@testuniversity.edu", result.getContactEmail());
        verify(providerRepository, times(1)).save(any(Provider.class));
    }

}