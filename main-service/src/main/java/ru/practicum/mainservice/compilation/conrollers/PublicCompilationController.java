package ru.practicum.mainservice.compilation.conrollers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.compilation.CompilationService;
import ru.practicum.mainservice.compilation.dto.CompilationDto;

import javax.validation.constraints.PositiveOrZero;
import java.util.List;

/**
 * Публичный API для работы с подборками событий
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/compilations")
public class PublicCompilationController {

    private final CompilationService compilationService;

    // Получение подборок событий
    @GetMapping
    public List<CompilationDto> getCompilations(
            @RequestParam(required = false) boolean pined, // false
            @RequestParam(required = false, defaultValue = "0") @PositiveOrZero int from,
            @RequestParam(required = false, defaultValue = "10") @PositiveOrZero int size
    ) {
        log.info("GET /compilations?pinned={}&from={}&size={} - Getting collections of events.",
                pined, from, size);
        return compilationService.getCompilations(pined, from, size);
    }

    // Получение подборки событий по его id
    @GetMapping("/{compId}")
    public CompilationDto getCompilation(
            @PathVariable long compId
    ) {
        log.info("GET /compilation/{} - Getting a selection of events by its id.", compId);
        return compilationService.getCompilation(compId);
    }
}
