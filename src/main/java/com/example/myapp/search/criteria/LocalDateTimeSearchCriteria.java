package com.example.myapp.search.criteria;

import com.example.myapp.search.enums.OperationType;
import com.example.myapp.search.strategy.LocalDateTimePredicateStrategy;
import com.example.myapp.search.strategy.PredicateStrategy;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDateTime;

public class LocalDateTimeSearchCriteria implements SearchCriteria<LocalDateTime> {

    @Getter
    private PredicateStrategy<LocalDateTime> strategy = new LocalDateTimePredicateStrategy();

    private String field;

    private String operation;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy HH:mm:ss")
    private LocalDateTime value;

    @Override
    public OperationType getOperation() {
        return OperationType.getOperation(operation);
    }

    @Override
    public String getField() {
        return field;
    }

    @Override
    public LocalDateTime getValue() {
        return value;
    }

}