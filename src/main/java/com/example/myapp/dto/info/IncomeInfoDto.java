package com.example.myapp.dto.info;

import com.example.myapp.dto.full.CategoryFullDto;
import com.example.myapp.dto.full.UserFullDto;
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
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IncomeInfoDto {

    private UUID id;

    @NotBlank(message = "Comment cannot be empty")
    private String comment;

    @Min(value = 0, message = "Value cannot be negative")
    private BigDecimal value;

    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate getDate;

    @Valid
    private UserInfoDto user;

    @Valid
    private CategoryInfoDto category;
}