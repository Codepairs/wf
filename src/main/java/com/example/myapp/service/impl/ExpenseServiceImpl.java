package com.example.myapp.service.impl;


import com.example.myapp.model.Expense;
import com.example.myapp.repository.ExpenseRepository;
import com.example.myapp.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class ExpenseServiceImpl implements ExpenseService {
    @Autowired
    private ExpenseRepository expenseRepository;

    @Override
    public void create(Expense expense) {
        expenseRepository.save(expense);
    }


    @Override
    public List<Expense> readAll() {
        return expenseRepository.findAll();
    }

    @Override
    public Expense read(Long id) {
        return expenseRepository.getById(id);
    }

    @Override
    public List<Expense> readAllByCategoryId(Long id) { return expenseRepository.findAllBycategory_id(id); }


    @Override
    public boolean update(Expense income, Long id) {
        if (expenseRepository.existsById((id))) {
            income.setId(id);
            expenseRepository.save(income);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Long id) {
        if (expenseRepository.existsById(id)) {
            expenseRepository.deleteById(id);
            return true;
        }
        return false;
    }
}


