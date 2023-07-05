package ru.practicum.mainservice.compilation.conrollers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.compilation.dto.CompilationDto;
import ru.practicum.mainservice.exceptions.BadRequestException;

import javax.validation.constraints.PositiveOrZero;
import java.util.List;

/**
 * Публичный API для работы с подборками событий
 */
@Slf4j
@RestController
@RequestMapping("/compilations")
public class PublicCompilationController {

    // Получение подборок событий
    @GetMapping
    public List<CompilationDto> getCompilations(
            @RequestParam(required = false) boolean pined, // false
            @RequestParam(required = false, defaultValue = "0") @PositiveOrZero int from,
            @RequestParam(required = false, defaultValue = "10") @PositiveOrZero int size
    ) {
        log.info("GET /compilations?pinned={}&from={}&size={} - Получение подборок событий.", pined, from, size);
        throw new BadRequestException("my message", "Incorrectly made request.");
        //return null;
    }

    // Получение подборки событий по его id
    @GetMapping("/{compId}")
    public CompilationDto getCompilation(
            @PathVariable long compId
    ) {
        log.info("GET /compilation/{} - Получение подборки событий по его id.", compId);
        return new CompilationDto();
    }
}
