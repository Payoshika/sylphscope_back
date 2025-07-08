package com.scholarship.scholarship.service;

import com.scholarship.scholarship.valueObject.CombinationLogic;
import com.scholarship.scholarship.valueObject.ConditionGroup;
import com.scholarship.scholarship.enums.LogicalOperator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CombinationLogicService {

    public CombinationLogic createCombinationLogic(LogicalOperator logicalOperator, List<ConditionGroup> conditionGroups) {
        if (logicalOperator == null || conditionGroups == null || conditionGroups.isEmpty()) {
            throw new IllegalArgumentException("Logical operator and conditionGroups are required");
        }
        return CombinationLogic.builder()
                .logicalOperator(logicalOperator)
                .conditionGroups(conditionGroups)
                .build();
    }
}