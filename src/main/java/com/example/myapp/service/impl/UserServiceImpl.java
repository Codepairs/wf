package com.example.myapp.service.impl;


import com.example.myapp.dto.full.ExpenseFullDto;
import com.example.myapp.dto.full.IncomeFullDto;
import com.example.myapp.dto.full.UserFullDto;
import com.example.myapp.dto.update.UserUpdateDto;
import com.example.myapp.exceptions.EmptyUsersException;
import com.example.myapp.exceptions.NotFoundByIdException;
import com.example.myapp.exceptions.SQLUniqueException;
import com.example.myapp.model.Expense;
import com.example.myapp.model.Income;
import com.example.myapp.model.User;
import com.example.myapp.repository.ExpenseRepository;
import com.example.myapp.repository.IncomeRepository;
import com.example.myapp.repository.UserRepository;
import com.example.myapp.service.UserService;
import com.example.myapp.utils.ExpenseMappingUtils;
import com.example.myapp.utils.IncomeMappingUtils;
import com.example.myapp.utils.UserMappingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private IncomeRepository incomeRepository;
    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private UserMappingUtils userMappingUtils;

    @Autowired
    private IncomeMappingUtils incomeMappingUtils;

    @Autowired
    private ExpenseMappingUtils expenseMappingUtils;

    @Override
    public UserFullDto create(UserUpdateDto user) throws SQLUniqueException {
        try {
        User newUser = userMappingUtils.userUpdateToUser(user);
        return userMappingUtils.userToUserFull(userRepository.save(newUser));
        }
        catch (Exception e) {
            throw new SQLUniqueException(e.getMessage());
        }
    }


    @Override
    public List<UserFullDto>  readAll() throws EmptyUsersException {
        List<UserFullDto> users = userRepository.findAll().stream().map(userMappingUtils::userToUserFull).toList();
        if (users.isEmpty()) {
            throw new EmptyUsersException("Users list is empty");
        }
        return users;
    }

    @Override
    public UserFullDto read(UUID id) throws NotFoundByIdException {
        if (!userRepository.existsById(id)) {
            throw new NotFoundByIdException("User with id " + id + " not found");
        }
        return userMappingUtils.userToUserFull(userRepository.getReferenceById(id));
    }


    @Override
    public UserFullDto update(UserUpdateDto user, UUID id) throws SQLUniqueException, NotFoundByIdException {
        if (!userRepository.existsById(id)) {
            throw new NotFoundByIdException("User with id " + id + " not found");
        }
        User user1 = userRepository.getReferenceById(id);
        try{
            user1.setExpenses(user.getExpenses());
            user1.setIncomes(user.getIncomes());
            user1.setName(user.getName());
            user1.setEmail(user.getEmail());
            user1.setPassword(user.getPassword());
            return userMappingUtils.userToUserFull(userRepository.save(user1));
        }
        catch (Exception e){
            throw new SQLUniqueException(e.getMessage());
        }
    }

    @Override
    public void delete(UUID id) throws NotFoundByIdException {
        if (!userRepository.existsById(id)) {
            throw new NotFoundByIdException("User with id " + id + " not found");
        }
        userRepository.deleteById(id);
    }

    public List<IncomeFullDto> getIncomes(UUID id) throws NotFoundByIdException {
        try{
            return incomeRepository.findAllByuser_id(id)
                    .stream().map(incomeMappingUtils::incomeToIncomeFull).toList();
        }
        catch (Exception e){
            throw new NotFoundByIdException("User with id " + id + " not found");
        }

    }

    public List<ExpenseFullDto> getExpenses(UUID id) throws NotFoundByIdException {
        try{
            return expenseRepository.findAllByuser_id(id)
                    .stream().map(expenseMappingUtils::expenseToExpenseFull).toList();
        }
        catch (Exception e){
            throw new NotFoundByIdException("User with id " + id + " not found");
        }
    }
}


