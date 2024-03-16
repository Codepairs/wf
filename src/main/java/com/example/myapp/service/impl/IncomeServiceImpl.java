package com.example.myapp.service.impl;


import com.example.myapp.model.Expense;
import com.example.myapp.model.Income;
import com.example.myapp.repository.IncomeRepository;
import com.example.myapp.service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class IncomeServiceImpl implements IncomeService {
    @Autowired
    private IncomeRepository incomeRepository;

    @Override
    public void create(Income income) {
        incomeRepository.save(income);
    }


    @Override
    public List<Income>  readAll() {
        return incomeRepository.findAll();
    }

    @Override
    public Income read(Long id) {
        return incomeRepository.getById(id);
    }

    @Override
    public List<Income> readAllByCategoryId(Long id) { return incomeRepository.findAllBycategory_id(id); }


    @Override
    public boolean update(Income income, Long id) {
        if (incomeRepository.existsById((id))) {
            income.setId(id);
            incomeRepository.save(income);
            return true;
        }

        return false;
    }

    @Override
    public boolean delete(Long id) {
        if (incomeRepository.existsById(id)) {
            incomeRepository.deleteById(id);
            return true;
        }
        return false;
    }
}


