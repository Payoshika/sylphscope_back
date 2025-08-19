package com.scholarship.scholarship.repository;

import com.scholarship.scholarship.model.Application;
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

class ApplicationRepositoryTest {
    @Mock
    private ApplicationRepository applicationRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findByStudentId() {
        Application app1 = new Application();
        app1.setId("1");
        app1.setStudentId("student1");
        Application app2 = new Application();
        app2.setId("2");
        app2.setStudentId("student1");
        when(applicationRepository.findByStudentId("student1")).thenReturn(Arrays.asList(app1, app2));
        List<Application> result = applicationRepository.findByStudentId("student1");
        assertEquals(2, result.size());
        assertEquals("student1", result.get(0).getStudentId());
    }

    @Test
    void findByGrantProgramId() {
        Application app1 = new Application();
        app1.setId("1");
        app1.setGrantProgramId("gp1");
        when(applicationRepository.findByGrantProgramId("gp1")).thenReturn(Arrays.asList(app1));
        List<Application> result = applicationRepository.findByGrantProgramId("gp1");
        assertEquals(1, result.size());
        assertEquals("gp1", result.get(0).getGrantProgramId());
    }

    @Test
    void findByStudentIdAndGrantProgramId() {
        Application app = new Application();
        app.setId("1");
        app.setStudentId("student1");
        app.setGrantProgramId("gp1");
        when(applicationRepository.findByStudentIdAndGrantProgramId("student1", "gp1")).thenReturn(Optional.of(app));
        Optional<Application> result = applicationRepository.findByStudentIdAndGrantProgramId("student1", "gp1");
        assertTrue(result.isPresent());
        assertEquals("student1", result.get().getStudentId());
        assertEquals("gp1", result.get().getGrantProgramId());
    }
}