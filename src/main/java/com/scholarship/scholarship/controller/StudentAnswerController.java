package com.scholarship.scholarship.controller;

import com.scholarship.scholarship.dto.StudentAnswerDto;
import com.scholarship.scholarship.model.StudentAnswer;
import com.scholarship.scholarship.modelmapper.StudentAnswerMapper;
import com.scholarship.scholarship.service.StudentAnswerService;
import com.scholarship.scholarship.service.GrantProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/student-answers")
public class StudentAnswerController {

    private final StudentAnswerService studentAnswerService;
    private final StudentAnswerMapper studentAnswerMapper;

    @Autowired
    public StudentAnswerController(StudentAnswerService studentAnswerService, StudentAnswerMapper studentAnswerMapper) {
        this.studentAnswerService = studentAnswerService;
        this.studentAnswerMapper = studentAnswerMapper;
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<StudentAnswerDto>> getAnswersByStudentId(@PathVariable String studentId) {
        List<StudentAnswerDto> dtos = studentAnswerService.getAnswersByStudentId(studentId)
                .stream()
                .map(studentAnswerMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/by-application")
    public ResponseEntity<List<StudentAnswerDto>> getAnswersByApplicationId(
            @RequestParam String studentId,
            @RequestParam String applicationId) {
        List<StudentAnswerDto> dtos = studentAnswerService.getAnswersByApplicationId(applicationId)
                .stream()
                .filter(answer -> answer.getStudentId() != null && answer.getStudentId().contains(studentId))
                .map(studentAnswerMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/by-student")
    public ResponseEntity<List<StudentAnswerDto>> getAnswersByStudentIdParam(@RequestParam String studentId) {
        List<StudentAnswerDto> dtos = studentAnswerService.getAnswersByStudentId(studentId)
                .stream()
                .collect(Collectors.toMap(
                        answer -> answer.getQuestionId() != null && !answer.getQuestionId().isEmpty() ? answer.getQuestionId() : answer.getQuestionGroupId(),
                        answer -> answer,
                        (a1, a2) -> a1.getAnsweredAt().isAfter(a2.getAnsweredAt()) ? a1 : a2
                ))
                .values()
                .stream()
                .map(studentAnswerMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentAnswerDto> getAnswerById(@PathVariable String id) {
        return studentAnswerService.getAnswerById(id)
                .map(studentAnswerMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<StudentAnswerDto> createAnswer(@RequestBody StudentAnswerDto answerDto) {
        StudentAnswer entity = studentAnswerMapper.toEntity(answerDto);
        StudentAnswer saved = studentAnswerService.saveAnswer(entity);
        return ResponseEntity.ok(studentAnswerMapper.toDto(saved));
    }

    @PutMapping("/update")
    public ResponseEntity<List<StudentAnswerDto>> updateAnswers(
            @RequestParam String studentId,
            @RequestParam String grantProgramId,
            @RequestBody List<StudentAnswerDto> answerDtos) {

        List<StudentAnswer> entities = answerDtos.stream()
                .map(studentAnswerMapper::toEntity)
                .collect(Collectors.toList());

        List<StudentAnswer> updatedAnswers = studentAnswerService.updateAnswers(studentId, grantProgramId, entities);
        List<StudentAnswerDto> dtos = updatedAnswers.stream()
                .map(studentAnswerMapper::toDto)
                .collect(Collectors.toList());


        return ResponseEntity.ok(dtos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnswer(@PathVariable String id) {
        studentAnswerService.deleteAnswer(id);
        return ResponseEntity.noContent().build();
    }
}