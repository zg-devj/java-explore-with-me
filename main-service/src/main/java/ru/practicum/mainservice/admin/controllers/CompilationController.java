package ru.practicum.mainservice.admin.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.dtos.CompilationDto;
import ru.practicum.mainservice.dtos.NewCompilationDto;
import ru.practicum.mainservice.dtos.UpdateCompilationRequest;

/**
 * API для работы с подборками событий
 */
@Slf4j
@RestController
@RequestMapping("/admin/compilations")
public class CompilationController {

    // Добавление новой подборки (подборка может не содержать событий)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationDto adminAddCompilation(
            @RequestBody NewCompilationDto newCompilationDto
    ) {
        log.info("POST /admin/compilations - Добавление новой подборки.");
        return null;
    }

    // Удаление подборки
    @DeleteMapping("/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void adminRemoveCompilation(
            @PathVariable long compId
    ) {
        log.info("POST /admin/compilations/{} - Добавление новой подборки.", compId);
    }

    // Обновить информацию о подборке
    @PatchMapping("/{compId}")
    public CompilationDto adminUpdateCompilation(
            @PathVariable long compId,
            @RequestBody UpdateCompilationRequest updateCompilationRequest
    ) {
        log.info("PATCH /admin/compilations/{} - Обновить информацию о подборке.", compId);
        return null;
    }
}
