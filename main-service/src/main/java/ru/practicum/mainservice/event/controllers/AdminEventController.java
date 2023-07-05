package ru.practicum.mainservice.event.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.event.dto.EventFullDto;
import ru.practicum.mainservice.event.dto.UpdateEventAdminRequest;

import javax.validation.constraints.PositiveOrZero;
import java.util.List;

/**
 * API для работы с событиями
 */
@Slf4j
@RestController
@RequestMapping("/admin/events")
public class AdminEventController {

    // Поиск событий
    @GetMapping
    public List<EventFullDto> adminFindEvents(
            @RequestParam List<Long> users,
            @RequestParam List<String> states,
            @RequestParam List<Long> categories,
            @RequestParam String rangeStart,
            @RequestParam String rangeEnd,
            @RequestParam(required = false, defaultValue = "0") @PositiveOrZero int from,
            @RequestParam(required = false, defaultValue = "10") @PositiveOrZero int size
    ) {
        // TODO: 01.07.2023 decode date
        log.info("DELETE /admin/events?users={}&stats={}&categories={}" +
                        "&rangeStart={}&rangeEnd={}&from={}&size={} - Поиск событий.",
                users, states, categories, rangeStart, rangeEnd, from, size);
        return null;
    }

    // Редактирование данных события и его статуса (отклонение/публикация).
    @PatchMapping("/{eventId}")
    public EventFullDto adminUpdateEvent(
            @PathVariable long eventId,
            @RequestBody UpdateEventAdminRequest updateEventAdminRequest
    ) {
        log.info("PATCH /admin/events/{} - " +
                "Редактирование данных события и его статуса (отклонение/публикация)..", eventId);
        return null;
    }
}
