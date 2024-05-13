package com.example.myapp.search.criteria;

import com.example.myapp.search.enums.OperationType;
import com.example.myapp.search.strategy.PredicateStrategy;
import com.example.myapp.search.strategy.UUIDPredicateStrategy;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.UUID;

public class UUIDSearchCriteria implements SearchCriteria<UUID> {

    @Getter
    private PredicateStrategy<UUID> strategy = new UUIDPredicateStrategy();

    private String field;

    private String operation;

    @NotNull
    private UUID value;

    @Override
    public OperationType getOperation() {
        return OperationType.getOperation(operation);
    }

    @Override
    public String getField() {
        return field;
    }

    @Override
    public UUID getValue() {
        return value;
    }

}
