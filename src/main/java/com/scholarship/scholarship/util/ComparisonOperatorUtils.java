package com.scholarship.scholarship.util;

import com.scholarship.scholarship.enums.ComparisonOperator;
import com.scholarship.scholarship.enums.InputType;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ComparisonOperatorUtils {
    public static List<ComparisonOperator> getOperatorsForInputType(InputType inputType) {
        if (inputType == null) return Collections.emptyList();
        switch (inputType) {
            case TEXT:
                return Arrays.asList(ComparisonOperator.EQUALS,ComparisonOperator.CONTAINS);
            case NUMBER:
            case DATE:
                return Arrays.asList(
                        ComparisonOperator.EQUALS,
                        ComparisonOperator.NOT_EQUALS,
                        ComparisonOperator.GREATER_THAN,
                        ComparisonOperator.LESS_THAN,
                        ComparisonOperator.GREATER_THAN_OR_EQUAL,
                        ComparisonOperator.LESS_THAN_OR_EQUAL
                );
            case RADIO:
                return Arrays.asList(
                        ComparisonOperator.EQUALS,
                        ComparisonOperator.IN_LIST
                );
            case MULTISELECT:
                return Collections.singletonList(ComparisonOperator.IN_LIST);
            default:
                return Collections.emptyList();
        }
    }
}