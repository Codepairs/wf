package com.example.myapp.utils;

import com.example.myapp.dto.full.ExpenseFullDto;
import com.example.myapp.dto.update.ExpenseUpdateDto;
import com.example.myapp.model.Expense;
import org.springframework.stereotype.Service;

@Service
public class ExpenseMappingUtils {

    public ExpenseFullDto expenseToExpenseFull(Expense expense) {
        return ExpenseFullDto.builder()
                .id(expense.getId())
                .comment(expense.getComment())
                .value(expense.getValue())
                .user(expense.getUser())
                .category(expense.getCategory())
                .lastUpdateTime(expense.getLastUpdateTime())
                .creationTime(expense.getCreationTime())
                .build();
    }
    public Expense expenseFullToExpense(ExpenseFullDto expenseFullDto) {
        return Expense.builder()
                .id(expenseFullDto.getId())
                .comment(expenseFullDto.getComment())
                .value(expenseFullDto.getValue())
                .user(expenseFullDto.getUser())
                .category(expenseFullDto.getCategory())
                .build();
    }

    public Expense expenseUpdateToExpense(ExpenseUpdateDto expenseUpdateDto) {
        return Expense.builder()
                .comment(expenseUpdateDto.getComment())
                .value(expenseUpdateDto.getValue())
                .user(expenseUpdateDto.getUser())
                .category(expenseUpdateDto.getCategory())
                .build();
    }

    public ExpenseUpdateDto expenseToExpenseUpdate(Expense expense) {
        return ExpenseUpdateDto.builder()
                .comment(expense.getComment())
                .value(expense.getValue())
                .build();
    }
}
