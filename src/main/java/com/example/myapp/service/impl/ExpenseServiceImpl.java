package com.example.myapp.service.impl;


import com.example.myapp.dto.create.ExpenseCreateDto;
import com.example.myapp.dto.info.ExpenseInfoDto;
import com.example.myapp.dto.search.ExpenseSearchDto;
import com.example.myapp.dto.update.ExpenseUpdateDto;
import com.example.myapp.handler.exceptions.EmptyExpenseException;
import com.example.myapp.handler.exceptions.NotFoundByIdException;
import com.example.myapp.handler.exceptions.SQLUniqueException;
import com.example.myapp.model.Category;
import com.example.myapp.model.Expense;
import com.example.myapp.model.User;
import com.example.myapp.repository.CategoryRepository;
import com.example.myapp.repository.ExpenseRepository;
import com.example.myapp.repository.UserRepository;
import com.example.myapp.service.ExpenseService;
import com.example.myapp.utils.MappingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


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
        Category category = mappingUtils.mapToCategory(expense.getCategory());
        try {
            newExpense.setCategory(category);
            newExpense.setComment(expense.getComment());
            newExpense.setValue(expense.getValue());
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
}


