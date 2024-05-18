package com.example.myapp.dto.expense;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExpenseUpdateDto {

    @NotBlank(message = "Comment cannot be empty")
    private String comment;

    @Min(value = 0, message = "Value cannot be negative")
    @NotNull(message = "Value cannot be null")
    private BigDecimal value;

    @Valid
    @NotNull(message = "CategoryId cannot be null")
    private UUID categoryId;

    @JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    @NotNull(message = "Date cannot be null")
    private LocalDateTime getDate;


}
