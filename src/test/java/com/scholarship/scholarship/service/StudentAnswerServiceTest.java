package com.scholarship.scholarship.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import com.scholarship.scholarship.model.StudentAnswer;
import com.scholarship.scholarship.repository.StudentAnswerRepository;
import com.scholarship.scholarship.repository.EligibilityCriteriaRepository;
import com.scholarship.scholarship.repository.EligibilityResultRepository;
import com.scholarship.scholarship.repository.ApplicationRepository;
import com.scholarship.scholarship.model.Application;
import java.util.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.time.Instant;

@ExtendWith(MockitoExtension.class)
class StudentAnswerServiceTest {
    @Mock
    private StudentAnswerRepository studentAnswerRepository;
    @Mock
    private EligibilityCriteriaRepository eligibilityCriteriaRepository;
    @Mock
    private EligibilityResultRepository eligibilityResultRepository;
    @Mock
    private ApplicationRepository applicationRepository;
    @InjectMocks
    private StudentAnswerService studentAnswerService;

    @Test
    void getAnswersByStudentId() {
        String studentId = "student1";
        StudentAnswer answer = StudentAnswer.builder().id("1").studentId(studentId).build();
        when(studentAnswerRepository.findByStudentId(studentId)).thenReturn(List.of(answer));
        List<StudentAnswer> result = studentAnswerService.getAnswersByStudentId(studentId);
        assertEquals(1, result.size());
        assertEquals(studentId, result.get(0).getStudentId());
    }

    @Test
    void getAnswersByApplicationId() {
        String applicationId = "app1";
        StudentAnswer answer = StudentAnswer.builder().id("2").applicationId(List.of(applicationId)).build();
        when(studentAnswerRepository.findByApplicationIdContaining(applicationId)).thenReturn(List.of(answer));
        List<StudentAnswer> result = studentAnswerService.getAnswersByApplicationId(applicationId);
        assertEquals(1, result.size());
        assertTrue(result.get(0).getApplicationId().contains(applicationId));
    }

    @Test
    void getAnswerById() {
        String id = "3";
        StudentAnswer answer = StudentAnswer.builder().id(id).build();
        when(studentAnswerRepository.findById(id)).thenReturn(Optional.of(answer));
        Optional<StudentAnswer> result = studentAnswerService.getAnswerById(id);
        assertTrue(result.isPresent());
        assertEquals(id, result.get().getId());
    }

    @Test
    void saveAnswer() {
        StudentAnswer answer = StudentAnswer.builder().id("4").studentId("student2").build();
        when(studentAnswerRepository.save(answer)).thenReturn(answer);
        StudentAnswer result = studentAnswerService.saveAnswer(answer);
        assertEquals("4", result.getId());
        assertEquals("student2", result.getStudentId());
    }

    @Test
    void updateAnswers() {
        String studentId = "student3";
        String grantProgramId = "grant1";
        StudentAnswer answer = StudentAnswer.builder()
                .id("5")
                .studentId(studentId)
                .applicationId(List.of("app2"))
                .questionId("q1")
                .answeredAt(Instant.now())
                .build();
        when(studentAnswerRepository.findByStudentIdAndQuestionId(studentId, "q1")).thenReturn(Optional.of(answer));
        when(studentAnswerRepository.save(any(StudentAnswer.class))).thenReturn(answer);
        Application application = Application.builder().id("app2").studentId(studentId).grantProgramId(grantProgramId).build();
        when(applicationRepository.findByStudentIdAndGrantProgramId(studentId, grantProgramId)).thenReturn(Optional.of(application));
        when(applicationRepository.save(any(Application.class))).thenReturn(application);
        // Add this mock to prevent RuntimeException
        when(applicationRepository.findById("app2")).thenReturn(Optional.of(application));
        List<StudentAnswer> result = studentAnswerService.updateAnswers(studentId, grantProgramId, List.of(answer));
        assertEquals(1, result.size());
        assertEquals(studentId, result.get(0).getStudentId());
    }

    @Test
    void deleteAnswer() {
        String id = "6";
        doNothing().when(studentAnswerRepository).deleteById(id);
        studentAnswerService.deleteAnswer(id);
        verify(studentAnswerRepository, times(1)).deleteById(id);
    }
}