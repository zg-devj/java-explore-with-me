package ru.practicum.mainservice.controllers.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.category.dto.CategoryDto;
import ru.practicum.mainservice.category.dto.NewCategoryDto;

/**
 * API для работы с категориями
 */
@Slf4j
@RestController
@RequestMapping("/admin/categories")
public class CategoryController {

    // Добавление новой категории
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto adminAddCategory(
            @RequestBody NewCategoryDto categoryDto
    ) {
        log.info("GET /admin/categories - Добавление новой категории.");
        return null;
    }

    // Удаление категории
    @DeleteMapping("/{catId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void adminRemoveCategory(
            @PathVariable long catId
    ) {
        log.info("DELETE /admin/categories/{} - Удаление категории.", catId);
    }

    // Изменение категории
    @PatchMapping("/{catId}")
    public CategoryDto adminUpdateCategory(
            @PathVariable long catId,
            @RequestBody CategoryDto categoryDto
    ) {
        log.info("PATCH /admin/categories/{} - Изменение категории.", catId);
        return null;
    }
}
