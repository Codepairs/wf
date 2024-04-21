package com.example.myapp.utils;

import com.example.myapp.dto.category.CategoryCreateDto;
import com.example.myapp.dto.expense.ExpenseCreateDto;
import com.example.myapp.dto.income.IncomeCreateDto;
import com.example.myapp.dto.user.UserCreateDto;
import com.example.myapp.dto.category.CategoryInfoDto;
import com.example.myapp.dto.expense.ExpenseInfoDto;
import com.example.myapp.dto.income.IncomeInfoDto;
import com.example.myapp.dto.user.UserInfoDto;
import com.example.myapp.handler.exceptions.NotFoundByIdException;
import com.example.myapp.model.Category;
import com.example.myapp.model.Expense;
import com.example.myapp.model.Income;
import com.example.myapp.model.User;
import com.example.myapp.repository.CategoryRepository;
import com.example.myapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MappingUtils {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;


    public Category mapToCategory(CategoryInfoDto categoryInfoDto) {
        return Category.builder().name(categoryInfoDto.getName()).build();
    }

    public Category mapToCategory(CategoryCreateDto categoryCreateDto) {
        return Category.builder().name(categoryCreateDto.getName()).build();
    }

    public CategoryInfoDto mapToCategoryInfoDto(Category category) {
        return CategoryInfoDto.builder().name(category.getName()).id(category.getId()).build();
    }

    public UserInfoDto mapToUserInfoDto(User user) {
        return UserInfoDto.builder().name(user.getName()).email(user.getEmail()).id(user.getId()).build();

    }

    public IncomeInfoDto mapToIncomeInfoDto(Income income) {
        return IncomeInfoDto.builder().id(income.getId()).comment(income.getComment()).value(income.getValue()).user(mapToUserInfoDto(income.getUser())).getDate(income.getGetDate()).category(mapToCategoryInfoDto(income.getCategory())).build();
    }

    public ExpenseInfoDto mapToExpenseInfoDto(Expense expense) {
        return ExpenseInfoDto.builder().id(expense.getId()).comment(expense.getComment()).value(expense.getValue()).user(mapToUserInfoDto(expense.getUser())).getDate(expense.getGetDate()).category(mapToCategoryInfoDto(expense.getCategory())).build();
    }


    public Income mapToIncome(IncomeInfoDto incomeInfoDto) {
        return Income.builder().comment(incomeInfoDto.getComment()).value(incomeInfoDto.getValue()).getDate(incomeInfoDto.getGetDate()).category(mapToCategory(incomeInfoDto.getCategory())).build();
    }

    public Income mapToIncome(IncomeCreateDto incomeCreateDto, Category category, User user) throws NotFoundByIdException {
        return Income.builder().comment(incomeCreateDto.getComment()).value(incomeCreateDto.getValue()).getDate(incomeCreateDto.getGetDate()).category(category).user(user).build();
    }

    public Expense mapToExpense(ExpenseCreateDto expenseCreateDto, Category category, User user) throws NotFoundByIdException {
        return Expense.builder().comment(expenseCreateDto.getComment()).value(expenseCreateDto.getValue()).getDate(expenseCreateDto.getGetDate()).category(category).user(user).build();
    }


    public User mapToUser(UserCreateDto userCreateDto) {
        return User.builder().name(userCreateDto.getName()).email(userCreateDto.getEmail()).password(userCreateDto.getPassword()).build();
    }

    public User mapToUser(UserInfoDto userInfoDto) {
        return User.builder().name(userInfoDto.getName()).email(userInfoDto.getEmail()).id(userInfoDto.getId()).build();
    }

    public UserInfoDto mapToUserInfo(User user) {
        return UserInfoDto.builder().name(user.getName()).email(user.getEmail()).id(user.getId()).build();
    }
}
