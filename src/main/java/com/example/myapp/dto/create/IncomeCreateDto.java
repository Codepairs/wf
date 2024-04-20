package com.example.myapp.dto.create;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IncomeCreateDto {

    @NotBlank(message = "Comment cannot be empty")
    private String comment;

    @Min(value = 0, message = "Value cannot be negative")
    private BigDecimal value;

    @NotNull(message = "CategoryId cannot be null")
    private UUID categoryId;

    @NotNull(message = "UserId cannot be null")
    private UUID userId;

    @JsonFormat(pattern = "dd.MM.yyyy")
    @NotNull(message = "Date cannot be null")
    private LocalDate getDate;


}
