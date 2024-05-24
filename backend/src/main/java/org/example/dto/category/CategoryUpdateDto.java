package org.example.dto.category;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryUpdateDto {

    @NotBlank(message = "Category name cannot be blank")
    private String name;
}
