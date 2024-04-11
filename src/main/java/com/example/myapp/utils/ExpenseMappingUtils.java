package com.example.myapp.utils;

import com.example.myapp.dto.full.CategoryFullDto;
import com.example.myapp.dto.full.ExpenseFullDto;
import com.example.myapp.dto.full.UserFullDto;
import com.example.myapp.dto.update.ExpenseUpdateDto;
import com.example.myapp.model.Category;
import com.example.myapp.model.Expense;
import com.example.myapp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExpenseMappingUtils {

    @Autowired
    private UserMappingUtils userMappingUtils;

    @Autowired
    private CategoryMappingUtils categoryMappingUtils;


    public ExpenseFullDto expenseToExpenseFull(Expense expense) {
        UserFullDto user = userMappingUtils.userToUserFull(expense.getUser());
        CategoryFullDto category = categoryMappingUtils.categoryToCategoryFull(expense.getCategory());
        return ExpenseFullDto.builder()
                .id(expense.getId())
                .comment(expense.getComment())
                .value(expense.getValue())
                .user(user)
                .category(category)
                .lastUpdateTime(expense.getLastUpdateTime())
                .creationTime(expense.getCreationTime())
                .build();
    }
    public Expense expenseFullToExpense(ExpenseFullDto expenseFullDto) {
        User user = userMappingUtils.userFullToUser(expenseFullDto.getUser());
        Category category = categoryMappingUtils.categoryFullToCategory(expenseFullDto.getCategory());
        return Expense.builder()
                .id(expenseFullDto.getId())
                .comment(expenseFullDto.getComment())
                .value(expenseFullDto.getValue())
                .user(user)
                .category(category)
                .build();
    }

    public Expense expenseUpdateToExpense(ExpenseUpdateDto expenseUpdateDto) {
        User user = userMappingUtils.userFullToUser(expenseUpdateDto.getUser());
        Category category = categoryMappingUtils.categoryFullToCategory(expenseUpdateDto.getCategory());
        return Expense.builder()
                .comment(expenseUpdateDto.getComment())
                .value(expenseUpdateDto.getValue())
                .user(user)
                .category(category)
                .build();
    }

    public ExpenseUpdateDto expenseToExpenseUpdate(Expense expense) {
        return ExpenseUpdateDto.builder()
                .comment(expense.getComment())
                .value(expense.getValue())
                .build();
    }
}
