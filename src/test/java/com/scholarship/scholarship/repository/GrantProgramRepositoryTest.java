package com.scholarship.scholarship.repository;

import com.scholarship.scholarship.model.GrantProgram;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GrantProgramRepositoryTest {
    @Mock
    private GrantProgramRepository grantProgramRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findByProviderId() {
        GrantProgram gp1 = new GrantProgram();
        gp1.setId("1");
        gp1.setProviderId("provider1");
        GrantProgram gp2 = new GrantProgram();
        gp2.setId("2");
        gp2.setProviderId("provider1");
        when(grantProgramRepository.findByProviderId("provider1")).thenReturn(Arrays.asList(gp1, gp2));
        List<GrantProgram> result = grantProgramRepository.findByProviderId("provider1");
        assertEquals(2, result.size());
        assertEquals("provider1", result.get(0).getProviderId());
    }

    @Test
    void findByTitleContainingIgnoreCase() {
        GrantProgram gp1 = new GrantProgram();
        gp1.setId("1");
        gp1.setTitle("Scholarship for Science");
        Pageable pageable = PageRequest.of(0, 10);
        Page<GrantProgram> page = new PageImpl<>(Arrays.asList(gp1), pageable, 1);
        when(grantProgramRepository.findByTitleContainingIgnoreCase("science", pageable)).thenReturn(page);
        Page<GrantProgram> result = grantProgramRepository.findByTitleContainingIgnoreCase("science", pageable);
        assertEquals(1, result.getTotalElements());
        assertTrue(result.getContent().get(0).getTitle().toLowerCase().contains("science"));
    }
}