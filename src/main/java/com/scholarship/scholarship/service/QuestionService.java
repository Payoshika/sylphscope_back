package com.scholarship.scholarship.service;

import com.scholarship.scholarship.dto.OptionDto;
import com.scholarship.scholarship.dto.QuestionDto;
import com.scholarship.scholarship.dto.QuestionOptionSetDto;
import com.scholarship.scholarship.dto.grantProgramDtos.QuestionEligibilityInfoDto;
import com.scholarship.scholarship.dto.grantProgramDtos.QuestionGroupEligibilityInfoDto;
import com.scholarship.scholarship.enums.ComparisonOperator;
import com.scholarship.scholarship.model.Option;
import com.scholarship.scholarship.model.Question;
import com.scholarship.scholarship.model.QuestionGroup;
import com.scholarship.scholarship.model.QuestionOptionSet;
import com.scholarship.scholarship.repository.QuestionOptionSetRepository;
import com.scholarship.scholarship.repository.QuestionRepository;
import com.scholarship.scholarship.repository.QuestionGroupRepository;
import com.scholarship.scholarship.enums.DataType;
import com.scholarship.scholarship.enums.InputType;
import com.scholarship.scholarship.exception.ResourceNotFoundException;
import com.scholarship.scholarship.util.ComparisonOperatorUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

@Service
@Slf4j
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final OptionService optionService;
    private final QuestionOptionSetRepository questionOptionSetRepository;
    private final QuestionGroupRepository questionGroupRepository;
    private final QuestionOptionSetService questionOptionSetService;

    @Autowired
    public QuestionService(
            QuestionRepository questionRepository,
            OptionService optionService,
            QuestionOptionSetRepository questionOptionSetRepository,
            QuestionGroupRepository questionGroupRepository,
            QuestionOptionSetService questionOptionSetService) {
        this.questionRepository = questionRepository;
        this.optionService = optionService;
        this.questionOptionSetRepository = questionOptionSetRepository;
        this.questionGroupRepository = questionGroupRepository;
        this.questionOptionSetService = questionOptionSetService;
    }

    public QuestionDto createQuestionWithOptions(QuestionDto questionDto, List<OptionDto> options) {
        validateInputTypeAndDataType(questionDto.getInputType(), questionDto.getQuestionDataType());
        if ((questionDto.getInputType() == InputType.RADIO || questionDto.getInputType() == InputType.MULTISELECT) && options != null && !options.isEmpty()) {
            // Save each option
            List<OptionDto> savedOptions = options.stream()
                    .map(optionService::createOption)
                    .toList();
            // Create QuestionOptionSet
            QuestionOptionSetDto optionSetDto = QuestionOptionSetDto.builder()
                    .optionSetLabel(questionDto.getName() + " Options")
                    .description("Options for " + questionDto.getName())
                    .optionDataType(DataType.STRING)
                    .options(savedOptions)
                    .build();

            QuestionOptionSetDto savedOptionSet = questionOptionSetService.createQuestionOptionSet(optionSetDto);

            // Set optionSetId in questionDto
            questionDto.setOptionSetId(savedOptionSet.getId());
        }

        Question question = Question.builder()
                .name(questionDto.getName())
                .questionText(questionDto.getQuestionText())
                .inputType(questionDto.getInputType())
                .questionDataType(questionDto.getQuestionDataType())
                .description(questionDto.getDescription())
                .isRequired(questionDto.isRequired())
                .requiresConditionalUpload(questionDto.getRequiresConditionalUpload())
                .conditionalUploadLabel(questionDto.getConditionalUploadLabel())
                .optionSetId(questionDto.getOptionSetId())
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

    public QuestionEligibilityInfoDto getQuestionEligibilityInfoById(String id) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Question not found with id: " + id));
        return questionForEligibility(question);
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
    public List<Question> getAllQuestionEntities() {
        return questionRepository.findAll();
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
                .isRequired(Boolean.TRUE.equals(question.getIsRequired()))
                .requiresConditionalUpload(Boolean.TRUE.equals(question.getRequiresConditionalUpload()))
                .conditionalUploadLabel(question.getConditionalUploadLabel())
                .optionSetId(question.getOptionSetId())
                .createdAt(question.getCreatedAt())
                .build();
    }

    private boolean requiresOptionSet(InputType inputType) {
        return inputType == InputType.RADIO || inputType == InputType.MULTISELECT;
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
                if (dataType != DataType.ARRAY) {
                    throw new IllegalArgumentException("RADIO input type must use ARRAY data type");
                }
            }
            case FILE_UPLOAD -> {
                if (dataType != DataType.STRING) {
                    throw new IllegalArgumentException("FILE_UPLOAD input type must use STRING data type");
                }
            }
        }
    }

    public List<QuestionEligibilityInfoDto> getQuestionsForEligibility() {
        return questionRepository.findAll().stream()
                .map(this::questionForEligibility)
                .toList();
    }

    public QuestionEligibilityInfoDto questionForEligibility(Question question) {
        List<Option> options = Collections.emptyList();
        if (question.getOptionSetId() != null) {
            Optional<QuestionOptionSet> optionSetOpt = questionOptionSetRepository.findById(question.getOptionSetId());
            if (optionSetOpt.isPresent()) {
                options = optionSetOpt.get().getOptions();
            }
        }
        List<ComparisonOperator> operators = ComparisonOperatorUtils.getOperatorsForInputType(question.getInputType());

        return QuestionEligibilityInfoDto.builder()
                .question(question)
                .options(options)
                .operators(operators)
                .build();
    }

    public List<QuestionGroupEligibilityInfoDto> getQuestionGroupsForEligibility() {
        List<QuestionGroup> groups = questionGroupRepository.findAll();
        System.out.println("Question Groups: " + groups.size());
        return groups.stream().map(group -> {
            List<QuestionEligibilityInfoDto> questions = group.getQuestionIds().stream()
                    .map(this::getQuestionById)
                    .filter(Objects::nonNull)
                    .map(q -> questionForEligibility(questionRepository.findById(q.getId()).orElse(null)))
                    .filter(Objects::nonNull)
                    .toList();
            QuestionGroupEligibilityInfoDto dto = new QuestionGroupEligibilityInfoDto();
            dto.setId(group.getId());
            dto.setName(group.getName());
            dto.setDescription(group.getDescription());
            dto.setQuestions(questions);
            return dto;
        }).toList();
    }



}