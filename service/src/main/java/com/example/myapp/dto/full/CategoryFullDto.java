package com.example.myapp.dto.full;


import com.example.myapp.model.Expense;
import com.example.myapp.model.Income;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryFullDto {

    private UUID id;

    @NotBlank(message = "Category name cannot be blank")
    private String name;

    @Valid
    private List<IncomeFullDto> incomes;

    @Valid
    private List<ExpenseFullDto> expenses;

    @JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    private LocalDateTime lastUpdateTime;

    @JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    private LocalDateTime creationTime;

}
