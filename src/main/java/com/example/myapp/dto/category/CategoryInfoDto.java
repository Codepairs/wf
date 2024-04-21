package com.example.myapp.dto.category;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryInfoDto {

    private UUID id;

    @NotBlank(message = "Category name cannot be blank")
    private String name;
}