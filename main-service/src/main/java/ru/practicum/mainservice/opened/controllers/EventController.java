package ru.practicum.mainservice.opened.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.dtos.EventFullDto;
import ru.practicum.mainservice.dtos.EventShortDto;

import javax.validation.constraints.PositiveOrZero;
import java.util.List;

/**
 * Публичный API для работы с событиями
 */
@Slf4j
@RestController
@RequestMapping("/events")
public class EventController {

    // Получение событий с возможностью фильтрации
    @GetMapping
    public List<EventShortDto> findEvents(
            @RequestParam String text,
            @RequestParam List<Long> categories,
            @RequestParam boolean paid,
            @RequestParam String rangeStart,
            @RequestParam String rangeEnd,
            @RequestParam(defaultValue = "false") boolean onlyAvailable,
            @RequestParam String sort, // EventSort
            @RequestParam(required = false, defaultValue = "0") @PositiveOrZero int from,
            @RequestParam(required = false, defaultValue = "10") @PositiveOrZero int size
    ) {
        log.info("GET /events?test={}&categories={}&paid={}&rangeStart={}" +
                        "&rangeEnd={}&onlyAvailable={}&sort={}&from={}&size={} - " +
                        "Получение событий с возможностью фильтрации.", text, categories, paid, rangeStart,
                rangeEnd, onlyAvailable, sort, from, size);
        return null;
    }

    // Получение подробной информации об опубликованном событии по его идентификатору
    @GetMapping("/{eventId}")
    public EventFullDto getEvent(
            @PathVariable long eventId
    ) {
        log.info("GET /events/{} - " +
                "Получение подробной информации об опубликованном событии по его идентификатору.", eventId);
        return null;
    }
}
