package com.example.myapp.service.impl;

import com.example.myapp.model.Expense;
import com.example.myapp.repository.ExpenseRepository;
import com.example.myapp.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;



}
