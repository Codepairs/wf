package com.example.myapp.dto.full;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IncomeFullDto {

    private UUID id;

    @NotBlank(message = "Comment can't be empty")
    private String comment;

    @Min(value = 0, message = "Value can't be negative")
    private Float value;

    @Valid
    private UserFullDto user;

    @Valid
    private CategoryFullDto category;

    @JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    private LocalDateTime lastUpdateTime;

    @JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    private LocalDateTime creationTime;

}
