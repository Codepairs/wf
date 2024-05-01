package com.example.myapp.dto.income;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IncomeSearchDto {
    @Positive
    @Builder.Default
    private int size = 100;

    @PositiveOrZero
    @Builder.Default
    private int page = 0;

}
