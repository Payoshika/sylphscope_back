package com.scholarship.scholarship.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import com.scholarship.scholarship.dto.StudentDto;
import com.scholarship.scholarship.model.Student;
import com.scholarship.scholarship.repository.StudentRepository;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;


    @Test
    void createMinimalStudent() {
        String userId = "user123";
        StudentDto dto = new StudentDto();
        dto.setUserId(userId);
        Student student = new Student();
        student.setUserId(userId);
        when(studentRepository.save(any(Student.class))).thenReturn(student);
        StudentDto result = studentService.createMinimalStudent(userId);
        assertEquals(userId, result.getUserId());
    }

    @Test
    void createStudent() {
        StudentDto dto = new StudentDto();
        dto.setUserId("user456");
        Student student = new Student();
        student.setUserId("user456");
        when(studentRepository.save(any(Student.class))).thenReturn(student);
        StudentDto result = studentService.createStudent(dto);
        assertEquals("user456", result.getUserId());
    }

    @Test
    void getStudentById() {
        Student student = new Student();
        student.setId("id1");
        student.setUserId("user789");
        when(studentRepository.findById("id1")).thenReturn(Optional.of(student));
        Optional<StudentDto> result = studentService.getStudentById("id1");
        assertTrue(result.isPresent());
        assertEquals("user789", result.get().getUserId());
    }

    @Test
    void getStudentByUserId() {
        Student student = new Student();
        student.setId("id2");
        student.setUserId("user101");
        when(studentRepository.findByUserId("user101")).thenReturn(Optional.of(student));
        Optional<StudentDto> result = studentService.getStudentByUserId("user101");
        assertTrue(result.isPresent());
        assertEquals("id2", result.get().getId());
    }

    @Test
    void getAllStudents() {
        Student student1 = new Student();
        student1.setId("id3");
        student1.setUserId("user111");
        Student student2 = new Student();
        student2.setId("id4");
        student2.setUserId("user222");
        when(studentRepository.findAll()).thenReturn(Arrays.asList(student1, student2));
        List<StudentDto> result = studentService.getAllStudents();
        assertEquals(2, result.size());
        assertEquals("user111", result.get(0).getUserId());
        assertEquals("user222", result.get(1).getUserId());
    }

    @Test
    void updateStudent() {
        StudentDto dto = new StudentDto();
        dto.setId("id5");
        dto.setUserId("user333");
        Student student = new Student();
        student.setId("id5");
        student.setUserId("userOld");
        when(studentRepository.findById("id5")).thenReturn(Optional.of(student));
        when(studentRepository.save(any(Student.class))).thenReturn(student);
        StudentDto result = studentService.updateStudent("id5", dto);
        assertEquals("user333", result.getUserId());
    }

    @Test
    void deleteStudent() {
        doNothing().when(studentRepository).deleteById("id6");
        studentService.deleteStudent("id6");
        verify(studentRepository, times(1)).deleteById("id6");
    }

    @Test
    void updateStudentWithWrongId() {
        StudentDto dto = new StudentDto();
        dto.setUserId("user999");
        when(studentRepository.findById("wrongId")).thenReturn(Optional.empty());
        StudentDto result = studentService.updateStudent("wrongId", dto);
        assertNull(result);
        verify(studentRepository, never()).save(any(Student.class));
    }

    @Test
    void updateStudentWithEmptyId() {
        StudentDto dto = new StudentDto();
        dto.setUserId("user999");
        when(studentRepository.findById("")).thenReturn(Optional.empty());
        StudentDto result = studentService.updateStudent("", dto);
        assertNull(result);
        verify(studentRepository, never()).save(any(Student.class));
    }

    @Test
    void deleteStudentWithWrongId() {
        doNothing().when(studentRepository).deleteById("wrongId");
        studentService.deleteStudent("wrongId");
        verify(studentRepository, times(1)).deleteById("wrongId");
    }

    @Test
    void deleteStudentWithEmptyId() {
        doNothing().when(studentRepository).deleteById("");
        studentService.deleteStudent("");
        verify(studentRepository, times(1)).deleteById("");
    }

    @Test
    void getStudentByUserIdWithWrongId() {
        when(studentRepository.findByUserId("wrongUserId")).thenReturn(Optional.empty());
        Optional<StudentDto> result = studentService.getStudentByUserId("wrongUserId");
        assertFalse(result.isPresent());
    }

    @Test
    void getStudentByUserIdWithEmptyId() {
        when(studentRepository.findByUserId("")).thenReturn(Optional.empty());
        Optional<StudentDto> result = studentService.getStudentByUserId("");
        assertFalse(result.isPresent());
    }

    @Test
    void getStudentByIdWithWrongId() {
        when(studentRepository.findById("wrongId")).thenReturn(Optional.empty());
        Optional<StudentDto> result = studentService.getStudentById("wrongId");
        assertFalse(result.isPresent());
    }

    @Test
    void getStudentByIdWithEmptyId() {
        when(studentRepository.findById("")).thenReturn(Optional.empty());
        Optional<StudentDto> result = studentService.getStudentById("");
        assertFalse(result.isPresent());
    }
}
