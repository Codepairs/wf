package com.example.myapp.search.criteria;

import com.example.myapp.search.enums.OperationType;
import com.example.myapp.search.strategy.PredicateStrategy;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.validation.constraints.NotNull;
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        visible = true,
        include = JsonTypeInfo.As.PROPERTY,
        property = "field")
@JsonSubTypes({
        @JsonSubTypes.Type(value = UUIDSearchCriteria.class, name = "id"),
        @JsonSubTypes.Type(value = StringSearchCriteria.class, name = "name"),
        @JsonSubTypes.Type(value = StringSearchCriteria.class, name = "email"),
        @JsonSubTypes.Type(value = StringSearchCriteria.class, name = "password"),
        @JsonSubTypes.Type(value = StringSearchCriteria.class, name = "comment"),
        @JsonSubTypes.Type(value = StringSearchCriteria.class, name = "category"),
        @JsonSubTypes.Type(value = BigDecimalSearchCriteria.class, name = "value"),
        @JsonSubTypes.Type(value = LocalDateTimeSearchCriteria.class, name = "lastUpdateTime"),
        @JsonSubTypes.Type(value = LocalDateTimeSearchCriteria.class, name = "getDate"),
        @JsonSubTypes.Type(value = LocalDateTimeSearchCriteria.class, name = "creationTime")
})
public interface SearchCriteria<T> {

    @NotNull(message = "Field cannot be null")
    String getField();

    @NotNull(message = "Value cannot be null")
    T getValue();

    @NotNull(message = "Operation cannot be null")
    OperationType getOperation();

    PredicateStrategy<T> getStrategy();
}
