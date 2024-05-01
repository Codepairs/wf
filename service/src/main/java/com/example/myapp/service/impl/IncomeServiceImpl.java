package com.example.myapp.service.impl;


import com.example.myapp.dto.income.IncomeCreateDto;
import com.example.myapp.dto.income.IncomeInfoDto;
import com.example.myapp.dto.income.IncomeSearchDto;
import com.example.myapp.dto.income.IncomeUpdateDto;
import com.example.myapp.handler.exceptions.EmptyIncomesException;
import com.example.myapp.handler.exceptions.NotFoundByIdException;
import com.example.myapp.handler.exceptions.SQLUniqueException;
import com.example.myapp.model.Category;
import com.example.myapp.model.Income;
import com.example.myapp.model.User;
import com.example.myapp.repository.CategoryRepository;
import com.example.myapp.repository.IncomeRepository;
import com.example.myapp.repository.UserRepository;
import com.example.myapp.service.IncomeService;
import com.example.myapp.utils.MappingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
public class IncomeServiceImpl implements IncomeService {

    @Autowired
    private IncomeRepository incomeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;


    @Autowired
    private MappingUtils mappingUtils;


    @Override
    public UUID create(IncomeCreateDto incomeCreateDto) throws SQLUniqueException, NotFoundByIdException {
        if (!userRepository.existsById(incomeCreateDto.getUserId())) {
            throw new NotFoundByIdException("User with id " + incomeCreateDto.getUserId() + " not found");

        }
        if (!categoryRepository.existsById(incomeCreateDto.getCategoryId())) {
            throw new NotFoundByIdException("Category with id " + incomeCreateDto.getCategoryId() + " not found");
        }
        try {
            Category category = categoryRepository.getReferenceById(incomeCreateDto.getCategoryId());
            User user = userRepository.getReferenceById(incomeCreateDto.getUserId());
            Income savedIncome = incomeRepository.save(mappingUtils.mapToIncome(incomeCreateDto, category, user));
            return savedIncome.getId();
        } catch (Exception e) {
            throw new SQLUniqueException(e.getMessage());
        }
    }


    @Override
    public List<IncomeInfoDto> readAll(IncomeSearchDto incomeSearchDto) throws EmptyIncomesException {
        int size = incomeSearchDto.getSize();
        int page = incomeSearchDto.getPage();

        PageRequest request = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "value"));
        List<IncomeInfoDto> incomes = incomeRepository.findAll(request).stream().map(mappingUtils::mapToIncomeInfoDto).toList();
        if (incomes.isEmpty()) {
            throw new EmptyIncomesException("Expenses not found");
        }
        return incomes;
    }

    @Override
    public IncomeInfoDto read(UUID id) throws NotFoundByIdException {
        if (!incomeRepository.existsById(id)) {
            throw new NotFoundByIdException("Expense with id " + id + " not found");
        }
        return mappingUtils.mapToIncomeInfoDto(incomeRepository.getReferenceById(id));
    }


    @Override
    public IncomeInfoDto update(IncomeUpdateDto income, UUID id) throws NotFoundByIdException, SQLUniqueException {
        if (!incomeRepository.existsById(id)) {
            throw new NotFoundByIdException("Expense with id " + id + " not found");
        }
        Income newIncome = incomeRepository.getReferenceById(id);
        if (!categoryRepository.existsById(income.getCategoryId())) {
            throw new NotFoundByIdException("Category with id " + income.getCategoryId() + " not found");
        }
        Category category = categoryRepository.getReferenceById(income.getCategoryId());
        try {
            newIncome.setCategory(category);
            newIncome.setComment(income.getComment());
            newIncome.setValue(income.getValue());
            newIncome.setGetDate(income.getGetDate());
            return mappingUtils.mapToIncomeInfoDto(incomeRepository.save(newIncome));
        } catch (Exception e) {
            throw new SQLUniqueException(e.getMessage());
        }

    }

    @Override
    public UUID delete(UUID id) throws NotFoundByIdException {
        if (!incomeRepository.existsById(id)) {
            throw new NotFoundByIdException("Expense with id " + id + " not found");
        }
        incomeRepository.deleteById(id);
        return id;
    }
}

