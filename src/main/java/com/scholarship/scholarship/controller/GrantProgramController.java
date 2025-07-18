package com.scholarship.scholarship.controller;

import com.scholarship.scholarship.dto.QuestionDto;
import com.scholarship.scholarship.dto.QuestionOptionSetDto;
import com.scholarship.scholarship.dto.grantProgramDtos.GrantProgramDto;
import com.scholarship.scholarship.dto.grantProgramDtos.QuestionEligibilityInfoDto;
import com.scholarship.scholarship.enums.DataType;
import com.scholarship.scholarship.enums.InputType;
import com.scholarship.scholarship.service.GrantProgramService;
import com.scholarship.scholarship.service.QuestionOptionSetService;
import com.scholarship.scholarship.service.QuestionService;
import com.scholarship.scholarship.dto.QuestionIdRequest;
import com.scholarship.scholarship.dto.QuestionGroupIdRequest;

import com.scholarship.scholarship.valueObject.Schedule;
import com.scholarship.scholarship.dto.OptionDto;
import com.scholarship.scholarship.model.Question;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/grant-programs")
@RequiredArgsConstructor
public class GrantProgramController {

    private final GrantProgramService grantProgramService;
    private final QuestionOptionSetService questionOptionSetService;
    private final QuestionService questionService;

    @PostMapping
    public ResponseEntity<GrantProgramDto> createGrantProgram(@Valid @RequestBody GrantProgramDto grantProgramDto) {
        GrantProgramDto created = grantProgramService.createGrantProgram(grantProgramDto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GrantProgramDto> getGrantProgramById(@PathVariable String id) {
        GrantProgramDto grantProgram = grantProgramService.getGrantProgramById(id);
        System.out.println(grantProgram);
        return ResponseEntity.ok(grantProgram);
    }

    @GetMapping
    public ResponseEntity<Page<GrantProgramDto>> getAllGrantPrograms(Pageable pageable) {
        Page<GrantProgramDto> grantPrograms = grantProgramService.getAllGrantPrograms(pageable);
        return ResponseEntity.ok(grantPrograms);
    }

    @GetMapping("/provider/{providerId}")
    public ResponseEntity<List<GrantProgramDto>> getGrantProgramsByProviderId(
            @PathVariable String providerId) {
        List<GrantProgramDto> grantPrograms = grantProgramService.getGrantProgramsByProviderId(providerId);
        return ResponseEntity.ok(grantPrograms);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GrantProgramDto> updateGrantProgram(
            @PathVariable String id, @Valid @RequestBody GrantProgramDto grantProgramDto) {
        System.out.println(id);
        System.out.println("updating");
        grantProgramDto.setId(id);
        GrantProgramDto updated = grantProgramService.updateGrantProgram(id, grantProgramDto);
        System.out.println(updated);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGrantProgram(@PathVariable String id) {
        grantProgramService.deleteGrantProgram(id);
        return ResponseEntity.noContent().build();
    }

    //add question group and question endpoints
    @PostMapping("/{grantProgramId}/questions")
    public ResponseEntity<GrantProgramDto> addQuestion(
            @PathVariable String grantProgramId,
            @RequestBody QuestionIdRequest request) {
        GrantProgramDto updated = grantProgramService.addQuestionToGrantProgram(grantProgramId, request.getQuestionId());
        return ResponseEntity.ok(updated);
    }

    @PostMapping("/{grantProgramId}/question-groups")
    public ResponseEntity<GrantProgramDto> addQuestionGroup(
            @PathVariable String grantProgramId,
            @RequestBody QuestionGroupIdRequest request) {
        GrantProgramDto updated = grantProgramService.addQuestionGroupToGrantProgram(grantProgramId, request.getQuestionGroupId());
        return ResponseEntity.ok(updated);
    }

    @PutMapping("/{grantProgramId}/schedule")
    public ResponseEntity<GrantProgramDto> updateSchedule(
            @PathVariable String grantProgramId,
            @RequestBody Schedule schedule) {
        GrantProgramDto updated = grantProgramService.updateSchedule(grantProgramId, schedule);
        return ResponseEntity.ok(updated);
    }

    @PutMapping("/questions/batch")
    public ResponseEntity<List<QuestionEligibilityInfoDto>> updateQuestionBatch(
            @RequestParam String grantProgramId,
            @RequestBody List<QuestionEligibilityInfoDto> questionDtos) {
        GrantProgramDto grantProgram = grantProgramService.getGrantProgramById(grantProgramId);
        if (grantProgram == null) {
            return ResponseEntity.notFound().build();
        }

        List<String> incomingQuestionIds = questionDtos.stream()
                .map(q -> q.getQuestion().getId())
                .filter(Objects::nonNull)
                .toList();

        List<String> existingQuestionIds = grantProgram.getQuestionIds() != null
                ? grantProgram.getQuestionIds()
                : List.of();
        List<String> allQuestionIds = questionService.getAllQuestionEntities().stream()
                .map(Question::getId)
                .toList();
        // Remove questions not present in incoming list
        for (String existingId : existingQuestionIds) {
            if (!incomingQuestionIds.contains(existingId)) {
                grantProgramService.removeQuestionFromGrantProgram(grantProgramId, existingId);
            }
        }
        for (QuestionEligibilityInfoDto dto : questionDtos) {
            QuestionDto questionDto = QuestionDto.builder()
                    .id(dto.getQuestion().getId())
                    .name(dto.getQuestion().getName())
                    .questionText(dto.getQuestion().getQuestionText())
                    .inputType(dto.getQuestion().getInputType())
                    .questionDataType(dto.getQuestion().getQuestionDataType())
                    .description(dto.getQuestion().getDescription())
                    .isRequired(dto.getQuestion().getIsRequired())
                    .build();
            System.out.println("question dto: " + questionDto);
            if (questionDto.getId() == null || !allQuestionIds.contains(questionDto.getId())) {
                System.out.println("creating new question");
                // Only for new questions: create option set if needed
                if ((questionDto.getInputType() == InputType.RADIO || questionDto.getInputType() == InputType.MULTISELECT)
                        && dto.getOptions() != null && !dto.getOptions().isEmpty()) {
                    QuestionOptionSetDto optionSetDto = QuestionOptionSetDto.builder()
                            .optionSetLabel(questionDto.getName() + " Options")
                            .description("Options for " + questionDto.getName())
                            .optionDataType(DataType.STRING)
                            .options(dto.getOptions().stream()
                                    .map(option -> OptionDto.builder()
                                            .label(option.getLabel())
                                            .value(option.getValue())
                                            .build())
                                    .toList())
                            .build();
                    QuestionOptionSetDto savedOptionSet = questionOptionSetService.createQuestionOptionSet(optionSetDto);
                    questionDto.setOptionSetId(savedOptionSet.getId());
                }
                QuestionDto created = questionService.createQuestionWithOptions(questionDto,
                        dto.getOptions().stream()
                                .map(option -> OptionDto.builder()
                                        .label(option.getLabel())
                                        .value(option.getValue())
                                        .build())
                                .toList());
                grantProgramService.addQuestionToGrantProgram(grantProgramId, created.getId());
            } else {
                // For existing questions, just update
                System.out.println("updating existing question");
                questionService.updateQuestion(questionDto.getId(), questionDto);
                grantProgramService.addQuestionToGrantProgram(grantProgramId, questionDto.getId());
            }
        }

        // Fetch all questions for the grant program and map to QuestionEligibilityInfoDto
        GrantProgramDto updated = grantProgramService.getGrantProgramById(grantProgramId);
        List<QuestionEligibilityInfoDto> result = updated.getQuestionIds().stream()
                .map(id -> {
                    Question question = questionService.getAllQuestionEntities().stream()
                            .filter(q -> id.equals(q.getId()))
                            .findFirst()
                            .orElse(null);
                    return question != null ? questionService.questionForEligibility(question) : null;
                })
                .filter(Objects::nonNull)
                .toList();

        return ResponseEntity.ok(result);
    }
}