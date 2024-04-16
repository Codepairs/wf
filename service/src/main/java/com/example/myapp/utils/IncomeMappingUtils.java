package com.example.myapp.utils;

import com.example.myapp.dto.full.IncomeFullDto;
import com.example.myapp.dto.update.IncomeUpdateDto;
import com.example.myapp.model.Income;
import jakarta.validation.constraints.Size;
import org.springframework.stereotype.Service;

@Service
public class IncomeMappingUtils {

    public IncomeFullDto incomeToIncomeFull(Income income) {
        return IncomeFullDto.builder()
                .id(income.getId())
                .comment(income.getComment())
                .value(income.getValue())
                .user(income.getUser())
                .category(income.getCategory())
                .lastUpdateTime(income.getLastUpdateTime())
                .creationTime(income.getCreationTime())
                .build();
    }

    public Income incomeFullToIncome(IncomeFullDto incomeFullDto) {
        return Income.builder()
                .id(incomeFullDto.getId())
                .comment(incomeFullDto.getComment())
                .value(incomeFullDto.getValue())
                .user(incomeFullDto.getUser())
                .category(incomeFullDto.getCategory())
                .build();
    }

    public Income incomeUpdateToIncome(IncomeUpdateDto incomeUpdateDto) {
        return Income.builder()
                .comment(incomeUpdateDto.getComment())
                .value(incomeUpdateDto.getValue())
                .user(incomeUpdateDto.getUser())
                .category(incomeUpdateDto.getCategory())
                .build();
    }

    public IncomeUpdateDto incomeToIncomeUpdate(Income income) {
        return IncomeUpdateDto.builder()
                .comment(income.getComment())
                .value(income.getValue())
                .build();
    }

}
