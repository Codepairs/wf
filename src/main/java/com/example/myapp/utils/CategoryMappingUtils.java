package com.example.myapp.utils;

import com.example.myapp.dto.full.CategoryFullDto;
import com.example.myapp.dto.update.CategoryUpdateDto;
import com.example.myapp.model.Category;
import org.springframework.stereotype.Service;

@Service
public class CategoryMappingUtils {
    public CategoryFullDto categoryToCategoryFull(Category category) {
        return CategoryFullDto.builder()
                .id(category.getId())
                .name(category.getName())
                .incomes(category.getIncomes())
                .expenses(category.getExpenses())
                .lastUpdateTime(category.getLastUpdateTime())
                .creationTime(category.getCreationTime())
                .build();
    }

    public Category categoryFullToCategory(CategoryFullDto categoryFullDto) {
        return Category.builder()
                .id(categoryFullDto.getId())
                .name(categoryFullDto.getName())
                .incomes(categoryFullDto.getIncomes())
                .expenses(categoryFullDto.getExpenses())
                .build();
    }

    public Category categoryUpdateToCategory(CategoryUpdateDto categoryUpdateDto) {
        return Category.builder()
                .name(categoryUpdateDto.getName())
                .incomes(categoryUpdateDto.getIncomes())
                .expenses(categoryUpdateDto.getExpenses())
                .build();
    }

    public CategoryUpdateDto categoryToCategoryUpdate(Category category) {
        return CategoryUpdateDto.builder()
                .name(category.getName())
                .incomes(category.getIncomes())
                .expenses(category.getExpenses())
                .build();
    }

}
