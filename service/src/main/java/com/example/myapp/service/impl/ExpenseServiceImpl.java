package com.example.myapp.service.impl;


import com.example.myapp.dto.category.CategoryInfoDto;
import com.example.myapp.dto.expense.ExpenseCreateDto;
import com.example.myapp.dto.expense.ExpenseInfoDto;
import com.example.myapp.dto.expense.ExpenseSearchDto;
import com.example.myapp.dto.expense.ExpenseUpdateDto;
import com.example.myapp.handler.exceptions.EmptyCategoriesException;
import com.example.myapp.handler.exceptions.EmptyExpenseException;
import com.example.myapp.handler.exceptions.NotFoundByIdException;
import com.example.myapp.handler.exceptions.SQLUniqueException;
import com.example.myapp.model.Category;
import com.example.myapp.model.Expense;
import com.example.myapp.model.User;
import com.example.myapp.repository.CategoryRepository;
import com.example.myapp.repository.ExpenseRepository;
import com.example.myapp.repository.UserRepository;
import com.example.myapp.search.criteria.SearchCriteria;
import com.example.myapp.search.enums.OperationType;
import com.example.myapp.search.strategy.PredicateStrategy;
import com.example.myapp.service.ExpenseService;
import com.example.myapp.utils.MappingUtils;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static org.springframework.data.util.CastUtils.cast;


@Service
public class ExpenseServiceImpl implements ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private MappingUtils mappingUtils;


    @Override
    public UUID create(ExpenseCreateDto expenseCreateDto) throws SQLUniqueException, NotFoundByIdException {
        if (!userRepository.existsById(expenseCreateDto.getUserId())) {
            throw new NotFoundByIdException("User with id " + expenseCreateDto.getUserId() + " not found");

        }
        if (!categoryRepository.existsById(expenseCreateDto.getCategoryId())) {
            throw new NotFoundByIdException("Category with id " + expenseCreateDto.getCategoryId() + " not found");
        }
        try {
            Category category = categoryRepository.getReferenceById(expenseCreateDto.getCategoryId());
            User user = userRepository.getReferenceById(expenseCreateDto.getUserId());
            Expense savedExpense = expenseRepository.save(mappingUtils.mapToExpense(expenseCreateDto, category, user));
            return savedExpense.getId();
        } catch (Exception e) {
            throw new SQLUniqueException(e.getMessage());
        }
    }


    @Override
    public List<ExpenseInfoDto> readAll(ExpenseSearchDto expenseSearchDto) throws EmptyExpenseException {
        int size = expenseSearchDto.getSize();
        int page = expenseSearchDto.getPage();

        PageRequest request = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "value"));
        List<ExpenseInfoDto> expenses = expenseRepository.findAll(request).stream().map(mappingUtils::mapToExpenseInfoDto).toList();
        if (expenses.isEmpty()) {
            throw new EmptyExpenseException("Expenses not found");
        }
        return expenses;
    }

    @Override
    public ExpenseInfoDto read(UUID id) throws NotFoundByIdException {
        if (!expenseRepository.existsById(id)) {
            throw new NotFoundByIdException("Expense with id " + id + " not found");
        }
        return mappingUtils.mapToExpenseInfoDto(expenseRepository.getReferenceById(id));
    }


    @Override
    public ExpenseInfoDto update(ExpenseUpdateDto expense, UUID id) throws NotFoundByIdException, SQLUniqueException {
        if (!expenseRepository.existsById(id)) {
            throw new NotFoundByIdException("Expense with id " + id + " not found");
        }
        Expense newExpense = expenseRepository.getReferenceById(id);
        if (!categoryRepository.existsById(expense.getCategoryId())){
            throw new NotFoundByIdException("Category with id" + expense.getCategoryId() + "not found");
        }

        Category category = categoryRepository.getReferenceById(expense.getCategoryId());
        try {
            newExpense.setCategory(category);
            newExpense.setComment(expense.getComment());
            newExpense.setValue(expense.getValue());
            newExpense.setGetDate(expense.getGetDate());
            return mappingUtils.mapToExpenseInfoDto(expenseRepository.save(newExpense));
        } catch (Exception e) {
            throw new SQLUniqueException(e.getMessage());
        }

    }

    @Override
    public UUID delete(UUID id) throws NotFoundByIdException {
        if (!expenseRepository.existsById(id)) {
            throw new NotFoundByIdException("Expense with id " + id + " not found");
        }
        expenseRepository.deleteById(id);
        return id;
    }

    @Override
    public List<ExpenseInfoDto> getByFilter(List<SearchCriteria<?>> conditions, Pageable pageable) throws EmptyCategoriesException {
        int size = pageable.getPageSize();
        int page = pageable.getPageNumber();

        Specification<Expense> specification = (entity, query, cb) -> {
            Predicate predicate = cb.conjunction();
            for (SearchCriteria<?> criteria : conditions) {
                OperationType operationType = criteria.getOperation();
                PredicateStrategy<?> strategy = criteria.getStrategy();
                switch (operationType) {
                    case EQUALS ->
                            predicate = cb.and(predicate, strategy.getEqualsPattern(entity.get(criteria.getField()), cast(criteria.getValue()), cb));
                    case RIGHT_LIMIT ->
                            predicate = cb.and(predicate, strategy.getRightLimitPattern(entity.get(criteria.getField()), cast(criteria.getValue()), cb));
                    case LEFT_LIMIT ->
                            predicate = cb.and(predicate, strategy.getLeftLimitPattern(entity.get(criteria.getField()), cast(criteria.getValue()), cb));
                    case LIKE ->
                            predicate = cb.and(predicate, strategy.getLikePattern(entity.get(criteria.getField()), cast(criteria.getValue()), cb));
                }
            }
            return predicate;
        };

        PageRequest request = PageRequest.of(page, size, pageable.getSort());
        Page<Expense> expenses = expenseRepository.findAll(specification, request);
        return expenses.getContent().stream().map(mappingUtils::mapToExpenseInfoDto).toList();
    }
}


