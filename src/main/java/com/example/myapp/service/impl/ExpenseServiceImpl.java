package com.example.myapp.service.impl;


import com.example.myapp.dto.full.ExpenseFullDto;
import com.example.myapp.dto.update.ExpenseUpdateDto;
import com.example.myapp.exceptions.EmptyExpenseException;
import com.example.myapp.exceptions.NotFoundByIdException;
import com.example.myapp.exceptions.SQLUniqueException;
import com.example.myapp.model.Expense;
import com.example.myapp.repository.ExpenseRepository;
import com.example.myapp.service.ExpenseService;
import com.example.myapp.utils.ExpenseMappingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;


@Service
public class ExpenseServiceImpl implements ExpenseService {
    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private ExpenseMappingUtils expenseMappingUtils;

    @Override
    public ExpenseFullDto create(ExpenseUpdateDto expense) throws SQLUniqueException {
        try{
            Expense newExpense = expenseMappingUtils.expenseUpdateToExpense(expense);
            return expenseMappingUtils.expenseToExpenseFull(expenseRepository.save(newExpense));
        }
        catch(Exception e){
            throw new SQLUniqueException(e.getMessage());
        }
    }


    @Override
    public List<ExpenseFullDto> readAll() throws EmptyExpenseException {
        List<ExpenseFullDto> expenses = expenseRepository.findAll().stream(
                ).map(expenseMappingUtils::expenseToExpenseFull).toList();
        if (expenses.isEmpty()){
            throw new EmptyExpenseException("Expense list is empty");
        }
        return expenses;
    }

    @Override
    public ExpenseFullDto read(UUID id) throws NotFoundByIdException {
        if (!expenseRepository.existsById(id)){
            throw new NotFoundByIdException("Expense with id " + id + " not found");
        }
        return expenseMappingUtils.expenseToExpenseFull(expenseRepository.getReferenceById(id));
    }


    @Override
    public ExpenseFullDto update(ExpenseUpdateDto expense, UUID id) throws NotFoundByIdException, SQLUniqueException {
        if (!expenseRepository.existsById(id)){
            throw new NotFoundByIdException("Expense with id " + id + " not found");
        }
        Expense newExpense = expenseRepository.getReferenceById(id);
        try{
            newExpense.setCategory(expense.getCategory());
            newExpense.setComment(expense.getComment());
            newExpense.setValue(expense.getValue());
            newExpense.setUser(expense.getUser());
            return expenseMappingUtils.expenseToExpenseFull(expenseRepository.save(newExpense));
        }
        catch (Exception e){
            throw new SQLUniqueException(e.getMessage());
        }

    }

    @Override
    public void delete(UUID id) throws NotFoundByIdException {
        if (!expenseRepository.existsById(id)){
            throw new NotFoundByIdException("Expense with id " + id + " not found");
        }
        expenseRepository.deleteById(id);
    }
}


