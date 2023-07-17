package ru.practicum.mainservice.category.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.category.CategoryService;
import ru.practicum.mainservice.category.dto.CategoryDto;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

/**
 * Публичный API для работы с категориями
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class PublicCategoryController {

    private final CategoryService categoryService;

    // Получение категорий
    @GetMapping
    public List<CategoryDto> getCategories(
            @RequestParam(defaultValue = "0") @PositiveOrZero int from,
            @RequestParam(defaultValue = "10") @Positive int size
    ) {
        log.info("GET /categories?from={}&size={} - Getting categories.", from, size);
        return categoryService.findAllCategories(from, size);
    }

    // Получение информации о категории по её идентификатору
    @GetMapping("/{catId}")
    public CategoryDto getCategory(
            @PathVariable Long catId
    ) {
        log.info("GET /categories/{} - Getting information about a category by its ID.", catId);
        return categoryService.findCategoryById(catId);
    }
}
