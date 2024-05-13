package com.example.myapp.search.criteria;

import com.example.myapp.search.enums.OperationType;
import com.example.myapp.search.strategy.PredicateStrategy;
import com.example.myapp.search.strategy.StringPredicateStrategy;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

public class StringSearchCriteria implements SearchCriteria<String> {

    @Getter
    private PredicateStrategy<String> strategy = new StringPredicateStrategy();

    private String field;

    private String operation;

    @NotBlank
    private String value;

    @Override
    public OperationType getOperation() {
        return OperationType.getOperation(operation);
    }

    @Override
    public String getField() {
        return field;
    }

    @Override
    public String getValue() {
        return value;
    }

}