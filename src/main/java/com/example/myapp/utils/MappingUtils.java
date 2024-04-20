package com.example.myapp.utils;

import com.example.myapp.dto.create.CategoryCreateDto;
import com.example.myapp.dto.create.ExpenseCreateDto;
import com.example.myapp.dto.create.IncomeCreateDto;
import com.example.myapp.dto.info.CategoryInfoDto;
import com.example.myapp.dto.info.ExpenseInfoDto;
import com.example.myapp.dto.info.IncomeInfoDto;
import com.example.myapp.dto.info.UserInfoDto;
import com.example.myapp.dto.service.CategoryDto;
import com.example.myapp.dto.service.ExpenseDto;
import com.example.myapp.dto.service.IncomeDto;
import com.example.myapp.dto.update.CategoryUpdateDto;
import com.example.myapp.model.Category;
import com.example.myapp.model.Expense;
import com.example.myapp.model.Income;
import com.example.myapp.model.User;
import org.springframework.stereotype.Service;

@Service
public class MappingUtils {

    public CategoryDto mapToCategory(CategoryCreateDto categoryCreateDto) {
        return CategoryDto.builder()
                .name(categoryCreateDto.getName()).build();
    }

    public CategoryDto mapToCategory(CategoryUpdateDto categoryUpdateDto) {
        return CategoryDto.builder()
                .name(categoryUpdateDto.getName()).build();
    }

    public Category mapToCategory(CategoryDto categoryDto) {
        return Category.builder()
                .name(categoryDto.getName()).build();
    }

    public Category mapToCategory(CategoryInfoDto categoryInfoDto) {
        return Category.builder()
                .name(categoryInfoDto.getName()).build();
    }

    public CategoryInfoDto mapToCategoryInfoDto(Category category) {
        return CategoryInfoDto.builder()
                .name(category.getName())
                .id(category.getId())
                .build();
    }

    public UserInfoDto mapToUserInfoDto(User user) {
        return UserInfoDto.builder()
                .name(user.getName())
                .email(user.getEmail())
                .id(user.getId())
                .build();

    }

    public IncomeInfoDto mapToIncomeInfoDto(Income income) {
        return IncomeInfoDto.builder()
                .id(income.getId())
                .comment(income.getComment())
                .value(income.getValue())
                .user(mapToUserInfoDto(income.getUser()))
                .getDate(income.getGetDate())
                .category(mapToCategoryInfoDto(income.getCategory()))
                .build();
    }

    public ExpenseInfoDto mapToExpenseInfoDto(Expense expense) {
        return ExpenseInfoDto.builder()
                .id(expense.getId())
                .comment(expense.getComment())
                .value(expense.getValue())
                .user(mapToUserInfoDto(expense.getUser()))
                .getDate(expense.getGetDate())
                .category(mapToCategoryInfoDto(expense.getCategory()))
                .build();
    }

    public ExpenseDto mapToExpense(ExpenseCreateDto expenseCreateDto) {
        return ExpenseDto.builder()
                .comment(expenseCreateDto.getComment())
                .value(expenseCreateDto.getValue())
                .getDate(expenseCreateDto.getGetDate())
                .category(expenseCreateDto.getCategory())
                .build();
    }


    public Expense mapToExpense(ExpenseDto expenseDto) {
        return Expense.builder()
                .comment(expenseDto.getComment())
                .value(expenseDto.getValue())
                .getDate(expenseDto.getGetDate())
                .category(mapToCategory(expenseDto.getCategory()))
                .build();
    }

    public Income mapToIncome(IncomeInfoDto incomeInfoDto) {
        return Income.builder()
                .comment(incomeInfoDto.getComment())
                .value(incomeInfoDto.getValue())
                .getDate(incomeInfoDto.getGetDate())
                .category(mapToCategory(incomeInfoDto.getCategory()))
                .build();
    }

    public Income mapToIncome(IncomeDto incomeDto) {
        return Income.builder()
                .comment(incomeDto.getComment())
                .value(incomeDto.getValue())
                .getDate(incomeDto.getGetDate())
                .category(mapToCategory(incomeDto.getCategory()))
                .build();
    }

    public IncomeDto mapToIncomeDto(IncomeCreateDto incomeCreateDto) {
        return IncomeDto.builder()
                .comment(incomeCreateDto.getComment())
                .value(incomeCreateDto.getValue())
                .getDate(incomeCreateDto.getGetDate())
                .category(incomeCreateDto.getCategory())
                .build();

    }
}
