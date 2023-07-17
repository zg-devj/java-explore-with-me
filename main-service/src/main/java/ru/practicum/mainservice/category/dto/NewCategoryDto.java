package ru.practicum.mainservice.category.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Данные для добавления новой категории
 */
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NewCategoryDto {

    // Название категории
    @NotBlank
    @Size(min = 1, max = 50)
    private String name;
}
