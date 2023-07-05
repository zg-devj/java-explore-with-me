package ru.practicum.mainservice.category.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.category.dto.CategoryDto;

import javax.validation.constraints.PositiveOrZero;
import java.util.List;

/**
 * Публичный API для работы с категориями
 */
@Slf4j
@RestController
@RequestMapping("/categories")
public class PublicCategoryController {

    // Получение категорий
    @GetMapping
    public List<CategoryDto> getCategories(
            @RequestParam(required = false, defaultValue = "0") @PositiveOrZero int from,
            @RequestParam(required = false, defaultValue = "10") @PositiveOrZero int size
    ) {
        log.info("GET /categories?from={}&size={} - Получение категорий.", from, size);
        return null;
    }

    // Получение информации о категории по её идентификатору
    @GetMapping("/{catId}")
    public CategoryDto getCategory(
            @PathVariable long catId
    ) {
        log.info("GET /categories/{} - Получение информации о категории по её идентификатору.", catId);
        return null;
    }
}