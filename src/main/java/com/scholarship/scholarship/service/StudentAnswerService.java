package com.scholarship.scholarship.service;

import com.scholarship.scholarship.dto.StudentAnswerDto;
import com.scholarship.scholarship.model.StudentAnswer;
import com.scholarship.scholarship.repository.StudentAnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentAnswerService {

    private final StudentAnswerRepository studentAnswerRepository;

    @Autowired
    public StudentAnswerService(StudentAnswerRepository studentAnswerRepository) {
        this.studentAnswerRepository = studentAnswerRepository;
    }

    public List<StudentAnswer> getAnswersByStudentId(String studentId) {
        return studentAnswerRepository.findByStudentId(studentId);
    }

    public Optional<StudentAnswer> getAnswerById(String id) {
        return studentAnswerRepository.findById(id);
    }

    public StudentAnswer saveAnswer(StudentAnswer answer) {
        return studentAnswerRepository.save(answer);
    }

    public List<StudentAnswer> updateAnswers(String studentId, String grantProgramId, List<StudentAnswer> answers) {
        List<StudentAnswer> updatedAnswers = answers.stream()
                .map(entity -> {
                    StudentAnswer answer = studentAnswerRepository
                            .findByStudentIdAndQuestionId(studentId, entity.getQuestionId())
                            .orElse(new StudentAnswer());
                    // Update fields from incoming entity
                    answer.setStudentId(studentId);
                    answer.setApplicationId(new String[]{grantProgramId});
                    answer.setQuestionId(entity.getQuestionId());
                    answer.setQuestionGroupId(entity.getQuestionGroupId());
                    answer.setAnswer(entity.getAnswer());
                    answer.setAnsweredAt(entity.getAnsweredAt());
                    answer.setUpdatedAt(entity.getUpdatedAt());
                    return studentAnswerRepository.save(answer);
                })
                .collect(Collectors.toList());
        return updatedAnswers;
    }

    public void deleteAnswer(String id) {
        studentAnswerRepository.deleteById(id);
    }
}