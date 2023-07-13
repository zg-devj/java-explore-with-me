package ru.practicum.mainservice.event.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.common.EndpointHitDto;
import ru.practicum.mainservice.event.EventService;
import ru.practicum.mainservice.event.EventSort;
import ru.practicum.mainservice.event.dto.EventFullDto;
import ru.practicum.mainservice.event.dto.EventShortDto;
import ru.practicum.mainservice.services.StatsService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Публичный API для работы с событиями
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
public class PublicEventController {

    private final EventService eventService;

    private final StatsService statsService;

    // Получение событий с возможностью фильтрации
    @GetMapping
    public List<EventShortDto> publicFindEvents(
            @RequestParam(required = false) String text,
            @RequestParam(required = false) List<Long> categories,
            @RequestParam(required = false) Boolean paid,
            @RequestParam(required = false) String rangeStart,
            @RequestParam(required = false) String rangeEnd,
            @RequestParam(defaultValue = "false") boolean onlyAvailable,
            @RequestParam EventSort sort, // EventSort
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
    public EventFullDto publicGetEvent(
            @PathVariable long eventId,
            HttpServletRequest request
    ) {
        log.info("GET /events/{} - " +
                "Получение подробной информации об опубликованном событии по его идентификатору.", eventId);
        EventFullDto eventFullDto = eventService.publicGetEvent(eventId);
        EndpointHitDto  hitDto = EndpointHitDto.builder()
                .app("main-service")
                .ip(request.getRemoteAddr())
                .uri(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();
        statsService.hit(hitDto);
        return eventFullDto;
    }
}
