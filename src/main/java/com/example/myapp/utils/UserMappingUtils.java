package com.example.myapp.utils;

import com.example.myapp.dto.full.ExpenseFullDto;
import com.example.myapp.dto.full.IncomeFullDto;
import com.example.myapp.dto.full.UserFullDto;
import com.example.myapp.dto.update.UserUpdateDto;
import com.example.myapp.model.Expense;
import com.example.myapp.model.Income;
import com.example.myapp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserMappingUtils {

    @Autowired
    private ExpenseMappingUtils expenseMappingUtils;

    @Autowired
    private IncomeMappingUtils incomeMappingUtils;

    public UserFullDto userToUserFull(User user) {
        List<ExpenseFullDto> expenses = user.getExpenses().stream(
                ).map(expenseMappingUtils::expenseToExpenseFull).toList();
        List<IncomeFullDto> incomes = user.getIncomes().stream(
                ).map(incomeMappingUtils::incomeToIncomeFull).toList();
        return UserFullDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .incomes(incomes)
                .expenses(expenses)
                .lastUpdateTime(user.getLastUpdateTime())
                .creationTime(user.getCreationTime())
                .build();
    }
    public User userFullToUser(UserFullDto userFullDto) {
        List<Expense> expenses = userFullDto.getExpenses().stream(
                ).map(expenseMappingUtils::expenseFullToExpense).toList();

        List<Income> incomes = userFullDto.getIncomes().stream(
                ).map(incomeMappingUtils::incomeFullToIncome).toList();

        return User.builder()
                .id(userFullDto.getId())
                .name(userFullDto.getName())
                .email(userFullDto.getEmail())
                .password(userFullDto.getPassword())
                .incomes(incomes)
                .expenses(expenses)
                .build();
    }

    public User userUpdateToUser(UserUpdateDto userFullDto) {
        List<Expense> expenses = userFullDto.getExpenses().stream(
                ).map(expenseMappingUtils::expenseFullToExpense).toList();
        List<Income> incomes = userFullDto.getIncomes().stream(
                ).map(incomeMappingUtils::incomeFullToIncome).toList();
        return User.builder()
                .name(userFullDto.getName())
                .email(userFullDto.getEmail())
                .password(userFullDto.getPassword())
                .incomes(incomes)
                .expenses(expenses)
                .build();
    }

    public UserFullDto userToUserUpdate(User user) {
        List<ExpenseFullDto> expenses = user.getExpenses().stream().map(expenseMappingUtils::expenseToExpenseFull).toList();
        List<IncomeFullDto> incomes = user.getIncomes().stream().map(incomeMappingUtils::incomeToIncomeFull).toList();

        return UserFullDto.builder()
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .incomes(incomes)
                .expenses(expenses)
                .build();
    }
}
