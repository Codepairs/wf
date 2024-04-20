package com.example.myapp.dto.update;

import com.example.myapp.dto.info.CategoryInfoDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IncomeUpdateDto {
    @NotBlank(message = "Comment can't be empty")
    private String comment;

    @Min(value = 0, message = "Value can't be negative")
    private BigDecimal value;

    @Valid
    private CategoryInfoDto category;

    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate getDate;

}
