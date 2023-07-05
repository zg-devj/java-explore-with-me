package ru.practicum.mainservice.category;

import ru.practicum.mainservice.category.dto.CategoryDto;
import ru.practicum.mainservice.category.dto.NewCategoryDto;

import java.util.List;

public interface CategoryService {
    // public
    CategoryDto findCategoryById(long categoryId);

    List<CategoryDto> findAllCategories(int form, int size);

    // admin
    CategoryDto createCategory(NewCategoryDto newCategoryDto);

    CategoryDto updateCategory(long categoryId,CategoryDto categoryDto);

    void deleteCategory(long categoryId);
}
