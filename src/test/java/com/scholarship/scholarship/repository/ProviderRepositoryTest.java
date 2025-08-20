package com.scholarship.scholarship.repository;

import com.scholarship.scholarship.model.Provider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProviderRepositoryTest {
    @Mock
    private ProviderRepository providerRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findByOrganisationName() {
        Provider provider = new Provider();
        provider.setId("1");
        provider.setOrganisationName("TestOrg");
        when(providerRepository.findByOrganisationName("TestOrg")).thenReturn(Optional.of(provider));
        Optional<Provider> result = providerRepository.findByOrganisationName("TestOrg");
        assertTrue(result.isPresent());
        assertEquals("TestOrg", result.get().getOrganisationName());
    }

    @Test
    void findByContactEmail() {
        Provider provider = new Provider();
        provider.setId("1");
        provider.setContactEmail("test@example.com");
        when(providerRepository.findByContactEmail("test@example.com")).thenReturn(Optional.of(provider));
        Optional<Provider> result = providerRepository.findByContactEmail("test@example.com");
        assertTrue(result.isPresent());
        assertEquals("test@example.com", result.get().getContactEmail());
    }
}