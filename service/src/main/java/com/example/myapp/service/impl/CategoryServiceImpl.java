package com.example.myapp.service.impl;


import com.example.myapp.model.Category;
import com.example.myapp.model.Expense;
import com.example.myapp.model.Income;
import com.example.myapp.repository.CategoryRepository;
import com.example.myapp.repository.ExpenseRepository;
import com.example.myapp.repository.IncomeRepository;
import com.example.myapp.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private IncomeRepository incomeRepository;
    @Autowired
    private ExpenseRepository expenseRepository;

    @Override
    public void create(Category category) {
        categoryRepository.save(category);
    }


    @Override
    public List<Category>  readAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category read(Long id) {
        return categoryRepository.getById(id);
    }


    @Override
    public boolean update(Category category, Long id) {
        if (categoryRepository.existsById((id))) {
            category.setId(id);
            categoryRepository.save(category);
            return true;
        }

        return false;
    }

    @Override
    public boolean delete(Long id) {
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Income> getIncomesByUserId(Long id) {
        return incomeRepository.findAllByuser_id(id);
    }

    public List<Expense> getExpensesByUserId(Long id) {
        return expenseRepository.findAllByuser_id(id);
    }

    public List<Expense> getExpensesByCategoryId(Long id) {
        return expenseRepository.findAllBycategory_id(id);
    }

    public List<Income> getIncomesByCategoryId(Long id) {
        return incomeRepository.findAllBycategory_id(id);
    }

}


