package ru.practicum.mainservice.category.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

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
    @Size(min = 1, max = 50)
    private String name;
}
