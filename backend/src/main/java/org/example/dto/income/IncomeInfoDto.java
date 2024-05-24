package org.example.dto.income;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.dto.category.CategoryInfoDto;
import org.example.dto.user.UserInfoDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
    @NotNull(message = "Value cannot be null")
    private BigDecimal value;

    @JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    private LocalDateTime getDate;

    @Valid
    private UserInfoDto user;

    @Valid
    private CategoryInfoDto category;
}
