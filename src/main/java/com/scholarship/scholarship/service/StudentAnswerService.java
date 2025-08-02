package com.scholarship.scholarship.service;

import com.scholarship.scholarship.dto.StudentAnswerDto;
import com.scholarship.scholarship.enums.ComparisonOperator;
import com.scholarship.scholarship.enums.EligibilityCriteriaType;
import com.scholarship.scholarship.model.StudentAnswer;
import com.scholarship.scholarship.repository.StudentAnswerRepository;
import com.scholarship.scholarship.valueObject.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scholarship.scholarship.model.EligibilityCriteria;
import com.scholarship.scholarship.model.EligibilityResult;
import com.scholarship.scholarship.repository.EligibilityCriteriaRepository;
import com.scholarship.scholarship.repository.EligibilityResultRepository;
import com.scholarship.scholarship.valueObject.QuestionCondition;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.time.LocalDate;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scholarship.scholarship.model.Application;
import com.scholarship.scholarship.repository.ApplicationRepository;

@Service
public class StudentAnswerService {

    private final StudentAnswerRepository studentAnswerRepository;

    @Autowired
    public StudentAnswerService(StudentAnswerRepository studentAnswerRepository) {
        this.studentAnswerRepository = studentAnswerRepository;
    }

    @Autowired
    private EligibilityCriteriaRepository eligibilityCriteriaRepository;
    @Autowired
    private EligibilityResultRepository eligibilityResultRepository;
    @Autowired
    private ApplicationRepository applicationRepository;

    public List<StudentAnswer> getAnswersByStudentId(String studentId) {
        return studentAnswerRepository.findByStudentId(studentId);
    }

    public List<StudentAnswer> getAnswersByApplicationId(String applicationId) {
        return studentAnswerRepository.findByApplicationIdContaining(applicationId);
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
                    answer.setApplicationId(entity.getApplicationId());
                    answer.setQuestionId(entity.getQuestionId());
                    answer.setQuestionGroupId(entity.getQuestionGroupId());
                    answer.setAnswer(entity.getAnswer());
                    answer.setAnsweredAt(entity.getAnsweredAt());
                    answer.setUpdatedAt(entity.getUpdatedAt());
                    return studentAnswerRepository.save(answer);
                })
                .collect(Collectors.toList());

        // Get or create Application
        Application application = applicationRepository.findByStudentIdAndGrantProgramId(studentId, grantProgramId).orElse(null);
        if (application == null) {
            application = Application.builder()
                    .studentId(studentId)
                    .grantProgramId(grantProgramId)
                    .status(com.scholarship.scholarship.enums.ApplicationStatus.DRAFT)
                    .submittedAt(null)
                    .updatedAt(java.time.Instant.now())
                    .build();
        }

        // Create a map of answers keyed by questionId/questionGroupId
        Map<String, StudentAnswer> studentAnswerMap = updatedAnswers.stream()
                .collect(Collectors.toMap(
                    answer -> answer.getQuestionId() != null && !answer.getQuestionId().isEmpty()
                            ? answer.getQuestionId()
                            : answer.getQuestionGroupId(),
                    answer -> answer
                ));

        // Set answers in application
        application.setStudentAnswers(studentAnswerMap);
        application = applicationRepository.save(application);

        // Eligibility check logic
        checkEligibility(studentId, grantProgramId, application.getId(), updatedAnswers);
        return updatedAnswers;
    }

    private void checkEligibility(String studentId, String grantProgramId, String applicationId, List<StudentAnswer> answers) {
        List<EligibilityCriteria> criteriaList = eligibilityCriteriaRepository.findByGrantProgramId(grantProgramId);
        List<String> passedCriteria = new ArrayList<>();
        List<String> failedCriteria = new ArrayList<>();
        // Build answerMap: key is questionId if present, else questionGroupId
        Map<String, StudentAnswer> answerMap = answers.stream()
                .collect(Collectors.toMap(
                        ans -> (ans.getQuestionId() != null && !ans.getQuestionId().isEmpty()) ? ans.getQuestionId() : ans.getQuestionGroupId(),
                        a -> a
                ));
        System.out.println("answerMap is");
        System.out.println(answerMap);
        boolean allPassed = true;
        for (EligibilityCriteria criteria : criteriaList) {
            boolean passed = false;
            if (criteria.getCriteriaType() == EligibilityCriteriaType.SINGLE) {
                // Simple question
                StudentAnswer ans = answerMap.get(criteria.getQuestionId());
                if (ans == null) {
                    // If no answer, false
                    passed = false;
                } else {
                    passed = checkCondition(ans.getAnswer().getFirst(), criteria.getSimpleCondition());
                }
            } else {
                // Group of questions
                List<QuestionCondition> conditions = criteria.getQuestionConditions();
                System.out.println("Checking group criteria: " + criteria.getId() + " with conditions: " + conditions);
                boolean groupPassed = true;
                StudentAnswer groupAnswer = answerMap.get(criteria.getQuestionGroupId());
                for (QuestionCondition cond : conditions) {
                    Answer answerForCond = null;
                    if (groupAnswer != null && groupAnswer.getAnswer() != null) {
                        // groupAnswer.getAnswer() is a list of objects (possibly Map or Answer)
                        List<Answer> groupAnswersList = groupAnswer.getAnswer();
                        //get the first element
                        for(Answer groupAnswerItem : groupAnswersList) {
                            if (groupAnswerItem.getQuestionId() != null && groupAnswerItem.getQuestionId().equals(cond.getQuestionId())) {
                                answerForCond = groupAnswerItem;
                                break;
                            }
                        }
                    }
                    System.out.println("Checking answer for question: " + cond.getQuestionId() + ", condition: " + cond + ", answer: " + (answerForCond != null ? answerForCond : "null"));
                    if (answerForCond == null) {
                        System.out.println("Answer for question " + cond.getQuestionId() + " is missing, group failed.");
                        groupPassed = false;
                        continue;
                    }
                    if (!checkCondition(answerForCond, cond)) {
                        groupPassed = false;
                        break;
                    }
                }
                passed = groupPassed;
            }
            if (passed) {
                passedCriteria.add(criteria.getId());
            } else {
                failedCriteria.add(criteria.getId());
                allPassed = false;
            }
        }
        Optional<EligibilityResult> existingResultOpt = eligibilityResultRepository.findByApplicationId(applicationId);
        EligibilityResult result;
        if (existingResultOpt.isPresent()) {
            result = existingResultOpt.get();
            result.setStudentId(studentId);
            result.setGrantProgramId(grantProgramId);
            result.setEligible(allPassed);  // Changed from setIsEligible to setEligible
            result.setPassedCriteria(passedCriteria);
            result.setFailedCriteria(failedCriteria);
            result.setEvaluatedAt(java.time.Instant.now());
        } else {
            result = EligibilityResult.builder()
                    .applicationId(applicationId)
                    .studentId(studentId)
                    .grantProgramId(grantProgramId)
                    .eligible(allPassed)  // Changed from isEligible to eligible
                    .passedCriteria(passedCriteria)
                    .failedCriteria(failedCriteria)
                    .evaluatedAt(java.time.Instant.now())
                    .build();
        }
        // Save EligibilityResult
        EligibilityResult savedResult = eligibilityResultRepository.save(result);

        // Update Application's eligibilityResult
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found with id: " + applicationId));
        application.setEligibilityResult(savedResult);
        applicationRepository.save(application);
    }

    private boolean checkCondition(Answer answer, QuestionCondition condition) {
        System.out.println("Checking condition: " + condition + " for answer: " + answer);
        if (condition == null) return true;
        if (answer == null) return false;
        System.out.println(condition.getComparisonOperator());
        System.out.println(condition.getComparisonOperator() == ComparisonOperator.EQUALS);

        ComparisonOperator operator = condition.getComparisonOperator();
        List<Object> values = condition.getValues();
        String dataType = condition.getValueDataType() != null ? condition.getValueDataType().name() : null;
        System.out.println("DataType: " + dataType);
        if (operator == null || dataType == null) return false;
        boolean result = false;
        switch (operator) {
            case ComparisonOperator.EQUALS:
                if (values == null || values.size() != 1) return false;
                Object expected = values.get(0);
                result = compareByType(answer, expected, dataType) == 0;
                break;
            case ComparisonOperator.IN_LIST:
                System.out.println("Checking IN_LIST operator");
                if (values == null || values.isEmpty()) return false;
                for (Object ans : answer.getAnswer()) {
                    for (Object value : values) {
                        if (ans instanceof List) {
                            System.out.println("Checking " + ans + " in list");
                            // If the value is a list, check if the answer is in that list
                            // change ans to List
                            List<?> ansList = (List<?>) ans;
                            if (ansList.contains(value)) {
                                System.out.println("IN_LIST match found in list: " + ansList);
                                result = true;
                                break;
                            } else {
                                System.out.println("IN_LIST no match found in list: " + ansList);
                            }
                        }
                        //if answer is String
                        else if (dataType.equals("STRING") && ans instanceof String) {
                            System.out.println("Checking " + ans + " in " + value);
                            if (ans.toString().contains(value.toString())) {
                                System.out.println("IN_LIST match found: " + ans);
                                result = true;
                                break;
                            } else {
                                System.out.println("IN_LIST no match found: " + ans);
                            }
                        } else if (ans.equals(value)) {
                            System.out.println("IN_LIST match found: " + ans);
                            result = true;
                            break;
                        }
                    }
                    if (result) break;
                }
                break;
            case ComparisonOperator.GREATER_THAN:
                if (values == null || values.size() != 1) return false;
                Object gtValue = values.get(0);
                result = compareByType(answer, gtValue, dataType) > 0;
                break;
            case ComparisonOperator.LESS_THAN:
                if (values == null || values.size() != 1) return false;
                Object ltValue = values.get(0);
                result = compareByType(answer, ltValue, dataType) < 0;
                break;
            case ComparisonOperator.NOT_EQUALS:
                if (values == null || values.size() != 1) return false;
                Object notExpected = values.get(0);
                result = compareByType(answer, notExpected, dataType) != 0;
                break;
            case ComparisonOperator.CONTAINS:
                System.out.println("Checking CONTAINS operator");
                System.out.println("Answer: " + answer.getAnswer());
                if (values == null || values.isEmpty()) return false;
                for (Object ans : answer.getAnswer()) {
                    System.out.println("Checking answer value: " + values);
                    System.out.println("Answer: " + ans);
                    System.out.println(values.contains(ans));
                    if (dataType.equals("STRING")) {
                        for (Object v : values) {
                            // Check if v contains ans as a substring
                            if (v != null && ans != null && ans.toString().contains(v.toString())) {
                                System.out.println("contains (STRING) " + ans + " in " + v);
                                result = true;
                                break;
                            }
                        }
                        if (result) break;
                    } else {
                        if (values.contains(ans)) {
                            System.out.println("contains " + ans);
                            System.out.println(values);
                            result = true;
                            break;
                        }
                    }
                }
                break;
            default:
                result = false;
        }
        System.out.println("QuestionId: " + condition.getQuestionId() + ", Operator: " + operator + ", Values: " + values + ", Answer: " + answer.getAnswer() + ", DataType: " + dataType + ", Result: " + result);
        return result;
    }

    private int compareByType(Answer answer, Object value, String dataType) {
        if (answer == null || value == null) return -1;
        try {
            switch (dataType) {
                case "INTEGER":
                    Integer aInt = Integer.parseInt((String) answer.getAnswer().getFirst());
                    Integer vInt = value instanceof Integer ? (Integer) value : Integer.parseInt(value.toString());
                    return aInt.compareTo(vInt);
                case "DOUBLE":
                    Double aDouble = Double.parseDouble((String) answer.getAnswer().getFirst());
                    Double vDouble = value instanceof Double ? (Double) value : Double.parseDouble(value.toString());
                    return aDouble.compareTo(vDouble);
                case "BOOLEAN":
                    Boolean aBool = Boolean.parseBoolean((String) answer.getAnswer().getFirst());
                    Boolean vBool = value instanceof Boolean ? (Boolean) value : Boolean.parseBoolean(value.toString());
                    return aBool.compareTo(vBool);
                case "DATE":
                    // Expecting date as Map<String, String> with keys: year, month, day
                    Map<String, String> aDateMap = null;
                    Map<String, String> vDateMap = null;
                    if (answer.getAnswer().getFirst() instanceof Map) {
                        aDateMap = (Map<String, String>) answer.getAnswer().getFirst();
                    } else if (answer.getAnswer().getFirst() instanceof String) {
                        // Try to parse JSON string to map
                        try {
                            aDateMap = new ObjectMapper().readValue((String) answer.getAnswer().getFirst(), java.util.Map.class);
                        } catch (Exception e) {
                            return -1;
                        }
                    }
                    if (value instanceof Map) {
                        vDateMap = (Map<String, String>) value;
                    } else if (value instanceof String) {
                        try {
                            vDateMap = new ObjectMapper().readValue((String) value, java.util.Map.class);
                        } catch (Exception e) {
                            return -1;
                        }
                    }
                    if (aDateMap == null || vDateMap == null) return -1;
                    int aYear = Integer.parseInt(aDateMap.getOrDefault("year", "0"));
                    int aMonth = Integer.parseInt(aDateMap.getOrDefault("month", "0"));
                    int aDay = Integer.parseInt(aDateMap.getOrDefault("day", "0"));
                    int vYear = Integer.parseInt(vDateMap.getOrDefault("year", "0"));
                    int vMonth = Integer.parseInt(vDateMap.getOrDefault("month", "0"));
                    int vDay = Integer.parseInt(vDateMap.getOrDefault("day", "0"));
                    LocalDate aLocalDate = LocalDate.of(aYear, aMonth, aDay);
                    LocalDate vLocalDate = LocalDate.of(vYear, vMonth, vDay);
                    return aLocalDate.compareTo(vLocalDate);

                default:
                    return answer.getAnswer().getFirst().toString().compareTo(value.toString());
            }
        } catch (Exception e) {
            return -1;
        }
    }

    public void deleteAnswer(String id) {
        studentAnswerRepository.deleteById(id);
    }
}
