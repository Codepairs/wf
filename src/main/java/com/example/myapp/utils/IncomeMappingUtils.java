package com.example.myapp.utils;

import com.example.myapp.dto.full.CategoryFullDto;
import com.example.myapp.dto.full.IncomeFullDto;
import com.example.myapp.dto.full.UserFullDto;
import com.example.myapp.dto.update.IncomeUpdateDto;
import com.example.myapp.model.Category;
import com.example.myapp.model.Income;
import com.example.myapp.model.User;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IncomeMappingUtils {

    @Autowired
    private UserMappingUtils userMappingUtils;

    @Autowired
    private CategoryMappingUtils categoryMappingUtils;


    public IncomeFullDto incomeToIncomeFull(Income income) {
        UserFullDto user = userMappingUtils.userToUserFull(income.getUser());
        CategoryFullDto category = categoryMappingUtils.categoryToCategoryFull(income.getCategory());
        return IncomeFullDto.builder()
                .id(income.getId())
                .comment(income.getComment())
                .value(income.getValue())
                .user(user)
                .category(category)
                .lastUpdateTime(income.getLastUpdateTime())
                .creationTime(income.getCreationTime())
                .build();
    }

    public Income incomeFullToIncome(IncomeFullDto incomeFullDto) {
        User user = userMappingUtils.userFullToUser(incomeFullDto.getUser());
        Category category = categoryMappingUtils.categoryFullToCategory(incomeFullDto.getCategory());
        return Income.builder()
                .id(incomeFullDto.getId())
                .comment(incomeFullDto.getComment())
                .value(incomeFullDto.getValue())
                .user(user)
                .category(category)
                .build();
    }

    public Income incomeUpdateToIncome(IncomeUpdateDto incomeUpdateDto) {
        User user = userMappingUtils.userFullToUser(incomeUpdateDto.getUser());
        Category category = categoryMappingUtils.categoryFullToCategory(incomeUpdateDto.getCategory());
        return Income.builder()
                .comment(incomeUpdateDto.getComment())
                .value(incomeUpdateDto.getValue())
                .user(user)
                .category(category)
                .build();
    }

    public IncomeUpdateDto incomeToIncomeUpdate(Income income) {
        return IncomeUpdateDto.builder()
                .comment(income.getComment())
                .value(income.getValue())
                .build();
    }

}
