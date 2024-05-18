package com.example.myapp.search.criteria;

import com.example.myapp.search.enums.OperationType;
import com.example.myapp.search.strategy.BigDecimalPredicateStrategy;
import com.example.myapp.search.strategy.PredicateStrategy;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.math.BigDecimal;

public class BigDecimalSearchCriteria implements SearchCriteria<BigDecimal> {

    @Getter
    private PredicateStrategy<BigDecimal> strategy = new BigDecimalPredicateStrategy();

    private String field;

    private String operation;

    @NotNull
    private BigDecimal value;

    @Override
    public OperationType getOperation() {
        return OperationType.getOperation(operation);
    }

    @Override
    public String getField() {
        return field;
    }

    @Override
    public BigDecimal getValue() {
        return value;
    }

}