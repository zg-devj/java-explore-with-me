package ru.practicum.mainservice.category.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Категория
 */
@Builder
@Getter
@Setter
public class CategoryDto {
    // Идентификатор категории
    private Long id;

    //Название категории
    private String name;
}
