package com.scholarship.scholarship.service;

import com.scholarship.scholarship.dto.StudentDto;
import com.scholarship.scholarship.model.Student;
import com.scholarship.scholarship.repository.StudentRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public StudentDto createMinimalStudent(String userId) {
        StudentDto studentDto = new StudentDto();
        studentDto.setUserId(userId);
        return createStudent(studentDto);
    }

    public StudentDto createStudent(StudentDto studentDto) {
        Student student = new Student();
        BeanUtils.copyProperties(studentDto, student);
        Student savedStudent = studentRepository.save(student);
        BeanUtils.copyProperties(savedStudent, studentDto);
        return studentDto;
    }

    public Optional<StudentDto> getStudentById(String id) {
        return studentRepository.findById(id)
                .map(student -> {
                    StudentDto dto = new StudentDto();
                    BeanUtils.copyProperties(student, dto);
                    return dto;
                });
    }

    public Optional<StudentDto> getStudentByUserId(String userId) {
        return studentRepository.findByUserId(userId)
                .map(student -> {
                    StudentDto dto = new StudentDto();
                    BeanUtils.copyProperties(student, dto);
                    return dto;
                });
    }

    public List<StudentDto> getAllStudents() {
        return studentRepository.findAll().stream()
                .map(student -> {
                    StudentDto dto = new StudentDto();
                    BeanUtils.copyProperties(student, dto);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public StudentDto updateStudent(String id, StudentDto studentDto) {
        Optional<Student> studentOpt = studentRepository.findById(id);
        if (studentOpt.isPresent()) {
            Student student = studentOpt.get();
            BeanUtils.copyProperties(studentDto, student);
            Student updatedStudent = studentRepository.save(student);
            BeanUtils.copyProperties(updatedStudent, studentDto);
            return studentDto;
        }
        return null;
    }

    public void deleteStudent(String id) {
        studentRepository.deleteById(id);
    }
}