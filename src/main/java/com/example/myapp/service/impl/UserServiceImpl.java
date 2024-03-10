package com.example.myapp.service.impl;


import com.example.myapp.model.Expense;
import com.example.myapp.model.Income;
import com.example.myapp.model.User;
import com.example.myapp.repository.ExpenseRepository;
import com.example.myapp.repository.IncomeRepository;
import com.example.myapp.repository.UserRepository;
import com.example.myapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private IncomeRepository incomeRepository;
    @Autowired
    private ExpenseRepository expenseRepository;

    @Override
    public void create(User user) {
        userRepository.save(user);
    }


    @Override
    public List<User>  readAll() {
        return userRepository.findAll();
    }

    @Override
    public User read(Long id) {
        return userRepository.getById(id);
    }


    @Override
    public boolean update(User user, Long id) {
        if (userRepository.existsById((id))) {
            user.setId(id);
            userRepository.save(user);
            return true;
        }

        return false;
    }

    @Override
    public boolean delete(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Income> getIncomes(Long id) {
        return incomeRepository.findAllByuser_id(id);
    }

    public List<Expense> getExpenses(Long id) {
        return expenseRepository.findAllByuser_id(id);
    }
}


