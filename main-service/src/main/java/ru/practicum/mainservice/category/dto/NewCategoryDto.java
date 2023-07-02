package ru.practicum.mainservice.category.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

/**
 * Данные для добавления новой категории
 */
public class NewCategoryDto {

    // Название категории
    @NotEmpty
    @Min(1)
    @Max(50)
    private String name;
}
