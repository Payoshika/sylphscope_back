package com.scholarship.scholarship.service;

import com.scholarship.scholarship.dto.QuestionDto;
import com.scholarship.scholarship.model.Question;
import com.scholarship.scholarship.repository.QuestionRepository;
import com.scholarship.scholarship.enums.DataType;
import com.scholarship.scholarship.enums.InputType;
import com.scholarship.scholarship.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuestionService {
    private final QuestionRepository questionRepository;

    public QuestionDto createQuestion(QuestionDto questionDto) {
        log.info("Creating new question: {} with inputType: {} and dataType: {}",
                questionDto.getName(), questionDto.getInputType(), questionDto.getQuestionDataType());

        // Validate input type and data type combination
        validateInputTypeAndDataType(questionDto.getInputType(), questionDto.getQuestionDataType());

        Question question = Question.builder()
                .name(questionDto.getName())
                .questionText(questionDto.getQuestionText())
                .inputType(questionDto.getInputType())
                .questionDataType(questionDto.getQuestionDataType())
                .description(questionDto.getDescription())
                .isRequired(questionDto.isRequired())
                .requiresConditionalUpload(questionDto.getRequiresConditionalUpload())
                .conditionalUploadLabel(questionDto.getConditionalUploadLabel())
                .createdAt(Instant.now())
                .build();

        Question savedQuestion = questionRepository.save(question);
        log.info("Question created successfully with id: {}", savedQuestion.getId());

        return mapToDto(savedQuestion);
    }

    public QuestionDto getQuestionById(String id) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Question not found with id: " + id));
        return mapToDto(question);
    }

    public Optional<QuestionDto> getQuestionByName(String name) {
        return questionRepository.findByName(name)
                .map(this::mapToDto);
    }

    public List<QuestionDto> getAllQuestions() {
        return questionRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    public QuestionDto updateQuestion(String id, QuestionDto questionDto) {
        Question existingQuestion = questionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Question not found with id: " + id));

        existingQuestion.setName(questionDto.getName());
        existingQuestion.setQuestionText(questionDto.getQuestionText());
        existingQuestion.setDescription(questionDto.getDescription());
        existingQuestion.setIsRequired(questionDto.isRequired());
        existingQuestion.setRequiresConditionalUpload(questionDto.getRequiresConditionalUpload());
        existingQuestion.setConditionalUploadLabel(questionDto.getConditionalUploadLabel());

        Question updatedQuestion = questionRepository.save(existingQuestion);
        return mapToDto(updatedQuestion);
    }

    public void deleteQuestion(String id) {
        if (!questionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Question not found with id: " + id);
        }
        questionRepository.deleteById(id);
        log.info("Question deleted successfully with id: {}", id);
    }

    private QuestionDto mapToDto(Question question) {
        return QuestionDto.builder()
                .id(question.getId())
                .name(question.getName())
                .questionText(question.getQuestionText())
                .inputType(question.getInputType())
                .questionDataType(question.getQuestionDataType())
                .description(question.getDescription())
                .isRequired(question.getIsRequired())
                .requiresConditionalUpload(question.getRequiresConditionalUpload())
                .conditionalUploadLabel(question.getConditionalUploadLabel())
                .createdAt(question.getCreatedAt())
                .build();
    }

    private void validateInputTypeAndDataType(InputType inputType, DataType dataType) {
        switch (inputType) {
            case TEXT, TEXTAREA -> {
                if (dataType != DataType.STRING) {
                    throw new IllegalArgumentException("TEXT and TEXTAREA input types must use STRING data type");
                }
            }
            case NUMBER -> {
                if (dataType != DataType.INTEGER && dataType != DataType.LONG && dataType != DataType.DOUBLE) {
                    throw new IllegalArgumentException("NUMBER input type must use INTEGER, LONG, or DOUBLE data type");
                }
            }
            case DATE -> {
                if (dataType != DataType.DATE && dataType != DataType.DATETIME) {
                    throw new IllegalArgumentException("DATE input type must use DATE or DATETIME data type");
                }
            }
            case MULTISELECT -> {
                if (dataType != DataType.ARRAY) {
                    throw new IllegalArgumentException("MULTISELECT input type must use ARRAY data type");
                }
            }
            case RADIO -> {
                if (dataType != DataType.STRING && dataType != DataType.INTEGER) {
                    throw new IllegalArgumentException("RADIO input type must use STRING or INTEGER data type");
                }
            }
            case FILE_UPLOAD -> {
                if (dataType != DataType.STRING) {
                    throw new IllegalArgumentException("FILE_UPLOAD input type must use STRING data type");
                }
            }
        }
    }
}