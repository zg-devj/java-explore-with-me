package ru.practicum.mainservice.category.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.category.CategoryService;
import ru.practicum.mainservice.category.dto.CategoryDto;
import ru.practicum.mainservice.category.dto.NewCategoryDto;

import javax.validation.Valid;

/**
 * API для работы с категориями
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/categories")
public class AdminCategoryController {

    private final CategoryService categoryService;

    // Добавление новой категории
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto adminAddCategory(
            @RequestBody @Valid NewCategoryDto newCategoryDto
    ) {
        log.info("GET /admin/categories - Adding a new category.");
        return categoryService.createCategory(newCategoryDto);
    }

    // Изменение категории
    @PatchMapping("/{catId}")
    public CategoryDto adminUpdateCategory(
            @PathVariable long catId,
            @RequestBody @Valid CategoryDto categoryDto
    ) {
        log.info("PATCH /admin/categories/{} - Changing the category.", catId);
        return categoryService.updateCategory(catId, categoryDto);
    }

    // Удаление категории
    @DeleteMapping("/{catId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void adminRemoveCategory(
            @PathVariable long catId
    ) {
        log.info("DELETE /admin/categories/{} - Deleting a category.", catId);
        categoryService.deleteCategory(catId);
    }
}
