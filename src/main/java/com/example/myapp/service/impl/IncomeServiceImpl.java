package com.example.myapp.service.impl;


import com.example.myapp.dto.info.IncomeInfoDto;
import com.example.myapp.dto.search.IncomeSearchDto;
import com.example.myapp.dto.service.IncomeDto;
import com.example.myapp.dto.update.IncomeUpdateDto;
import com.example.myapp.handler.exceptions.EmptyIncomesException;
import com.example.myapp.handler.exceptions.NotFoundByIdException;
import com.example.myapp.handler.exceptions.SQLUniqueException;
import com.example.myapp.model.Category;
import com.example.myapp.model.Income;
import com.example.myapp.repository.IncomeRepository;
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
    private MappingUtils mappingUtils;


    @Override
    public UUID create(IncomeDto income) throws SQLUniqueException {
        try {
            Income newIncome = mappingUtils.mapToIncome(income);
            incomeRepository.save(newIncome);

            return newIncome.getId();
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
        Category category = mappingUtils.mapToCategory(income.getCategory());
        try {
            newIncome.setCategory(category);
            newIncome.setComment(income.getComment());
            newIncome.setValue(income.getValue());
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

