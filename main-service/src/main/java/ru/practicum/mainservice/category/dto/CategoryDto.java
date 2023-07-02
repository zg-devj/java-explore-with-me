package ru.practicum.mainservice.category.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * Категория
 */
public class CategoryDto {
    // Идентификатор категории
    private Long id;

    //Название категории
    @Min(1)
    @Max(50)
    private String name;
}
