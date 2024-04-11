package com.example.myapp.utils;

import com.example.myapp.dto.full.CategoryFullDto;
import com.example.myapp.dto.full.ExpenseFullDto;
import com.example.myapp.dto.full.IncomeFullDto;
import com.example.myapp.dto.update.CategoryUpdateDto;
import com.example.myapp.model.Category;
import com.example.myapp.model.Expense;
import com.example.myapp.model.Income;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryMappingUtils {

    @Autowired
    private ExpenseMappingUtils expenseMappingUtils;

    @Autowired
    private IncomeMappingUtils incomeMappingUtils;

    public CategoryFullDto categoryToCategoryFull(Category category) {

        List<ExpenseFullDto> expenses = category.getExpenses().stream().map(expense -> expenseMappingUtils.expenseToExpenseFull(expense)).toList();
        List<IncomeFullDto> incomes = category.getIncomes().stream().map(income -> incomeMappingUtils.incomeToIncomeFull(income)).toList();
        return CategoryFullDto.builder()
                .id(category.getId())
                .name(category.getName())
                .incomes(incomes)
                .expenses(expenses)
                .lastUpdateTime(category.getLastUpdateTime())
                .creationTime(category.getCreationTime())
                .build();
    }

    public Category categoryFullToCategory(CategoryFullDto categoryFullDto) {
        List<Expense> expenses = categoryFullDto.getExpenses().stream().map(expense -> expenseMappingUtils.expenseFullToExpense(expense)).toList();
        List<Income> incomes = categoryFullDto.getIncomes().stream().map(income -> incomeMappingUtils.incomeFullToIncome(income)).toList();
        return Category.builder()
                .id(categoryFullDto.getId())
                .name(categoryFullDto.getName())
                .incomes(incomes)
                .expenses(expenses)
                .build();
    }

    public Category categoryUpdateToCategory(CategoryUpdateDto categoryUpdateDto) {
        List<Expense> expenses = null;
        List<Income> incomes = null;
        if (categoryUpdateDto.getExpenses() != null) {
            expenses = categoryUpdateDto.getExpenses().stream().map(expense -> expenseMappingUtils.expenseFullToExpense(expense)).toList();
        }
        if (categoryUpdateDto.getIncomes() != null){
            incomes = categoryUpdateDto.getIncomes().stream().map(income -> incomeMappingUtils.incomeFullToIncome(income)).toList();
        }
        return Category.builder()
                .name(categoryUpdateDto.getName())
                .incomes(incomes)
                .expenses(expenses)
                .build();
    }

    public CategoryUpdateDto categoryToCategoryUpdate(Category category) {
        List<ExpenseFullDto> expenses = category.getExpenses().stream().map(expense -> expenseMappingUtils.expenseToExpenseFull(expense)).toList();
        List<IncomeFullDto> incomes = category.getIncomes().stream().map(income -> incomeMappingUtils.incomeToIncomeFull(income)).toList();
        return CategoryUpdateDto.builder()
                .name(category.getName())
                .incomes(incomes)
                .expenses(expenses)
                .build();
    }

}
