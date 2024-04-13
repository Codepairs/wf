package com.example.myapp.dto.full;

import com.example.myapp.dto.full.ExpenseFullDto;
import com.example.myapp.dto.full.IncomeFullDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
public class UserFullDto {

    private UUID id;

    @Size(min = 3, message = "Name must be at least 3 characters long")
    @NotBlank(message = "Name cannot be empty")
    private String name;

    @Email
    @NotBlank(message = "Email cannot be empty")
    private String email;

    @NotBlank(message = "Password cannot be empty")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    @Valid
    private List<IncomeFullDto> incomes;

    @Valid
    private List<ExpenseFullDto> expenses;

    @JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    private LocalDateTime lastUpdateTime;

    @JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    private LocalDateTime creationTime;
}
