package com.example.myapp.dto.info;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryInfoDto {

    private UUID id;

    @NotBlank(message = "Category name cannot be blank")
    private String name;
}