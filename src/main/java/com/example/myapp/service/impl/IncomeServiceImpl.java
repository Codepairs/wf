package com.example.myapp.service.impl;


import com.example.myapp.dto.full.IncomeFullDto;
import com.example.myapp.dto.update.IncomeUpdateDto;
import com.example.myapp.handler.exceptions.EmptyIncomesException;
import com.example.myapp.handler.exceptions.NotFoundByIdException;
import com.example.myapp.handler.exceptions.SQLUniqueException;
import com.example.myapp.model.Category;
import com.example.myapp.model.Income;
import com.example.myapp.model.User;
import com.example.myapp.repository.IncomeRepository;
import com.example.myapp.service.IncomeService;
import com.example.myapp.utils.CategoryMappingUtils;
import com.example.myapp.utils.IncomeMappingUtils;
import com.example.myapp.utils.UserMappingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;


@Service
public class IncomeServiceImpl implements IncomeService {
    @Autowired
    private IncomeRepository incomeRepository;

    @Autowired
    private IncomeMappingUtils incomeMappingUtils;

    @Autowired
    private CategoryMappingUtils categoryMappingUtils;

    @Autowired
    private UserMappingUtils userMappingUtils;

    @Override
    public IncomeFullDto create(IncomeUpdateDto income) throws SQLUniqueException {
        try{
            Income income1 = incomeMappingUtils.incomeUpdateToIncome(income);
            return incomeMappingUtils.incomeToIncomeFull(incomeRepository.save(income1));
        }
        catch (Exception e){
            throw new SQLUniqueException(e.getMessage());
        }
    }


    @Override
    public List<IncomeFullDto> readAll() throws EmptyIncomesException {
        List<IncomeFullDto> incomes = incomeRepository.findAll().stream().map(incomeMappingUtils::incomeToIncomeFull).toList();
        if (incomes.isEmpty()) {
            throw new EmptyIncomesException("Incomes list is empty");
        }
        return incomes;
    }

    @Override
    public IncomeFullDto read(UUID id) throws NotFoundByIdException {
        if (!incomeRepository.existsById(id)) {
            throw new NotFoundByIdException("Income with id " + id + " not found");
        }
        return incomeMappingUtils.incomeToIncomeFull(incomeRepository.getReferenceById(id));
    }


    @Override
    public IncomeFullDto update(IncomeUpdateDto income, UUID id) throws NotFoundByIdException, SQLUniqueException {
        if (!incomeRepository.existsById(id)) {
            throw new NotFoundByIdException("Income with id " + id + " not found");
        }
        Income income1 = incomeRepository.getReferenceById(id);
        Category category = categoryMappingUtils.categoryFullToCategory(income.getCategory());
        User user = userMappingUtils.userFullToUser(income.getUser());
        try{
            income1.setCategory(category);
            income1.setComment(income.getComment());
            income1.setValue(income.getValue());
            income1.setUser(user);
            return incomeMappingUtils.incomeToIncomeFull(incomeRepository.save(income1));
        }
        catch (Exception e){
            throw new SQLUniqueException(e.getMessage());
        }
    }

    @Override
    public void delete(UUID id) throws NotFoundByIdException {
        if (!incomeRepository.existsById(id)) {
            throw new NotFoundByIdException("Income with id " + id + " not found");
        }
        incomeRepository.deleteById(id);
    }
}


