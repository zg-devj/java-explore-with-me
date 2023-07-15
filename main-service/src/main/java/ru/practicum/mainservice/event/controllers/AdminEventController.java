package ru.practicum.mainservice.event.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.event.EventService;
import ru.practicum.mainservice.event.EventState;
import ru.practicum.mainservice.event.dto.EventFullDto;
import ru.practicum.mainservice.event.dto.UpdateEventAdminRequest;
import ru.practicum.mainservice.event.dto.params.FindEventAdminParam;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.mainservice.utils.Util.DATE_TIME_FORMATTER;

/**
 * API для работы с событиями
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/events")
public class AdminEventController {

    private final EventService eventService;

    // Поиск событий
    @GetMapping
    public List<EventFullDto> adminFindEvents(
            @RequestParam(required = false) List<Long> users,
            @RequestParam(required = false) List<EventState> states,
            @RequestParam(required = false) List<Long> categories,
            @RequestParam(required = false) String rangeStart,
            @RequestParam(required = false) String rangeEnd,
            @RequestParam(defaultValue = "0") @PositiveOrZero int from,
            @RequestParam(defaultValue = "10") @Positive int size
    ) {
        LocalDateTime start = decodedDateTime(rangeStart);
        LocalDateTime end = decodedDateTime(rangeEnd);

        log.info("GET /admin/events?users={}&stats={}&categories={}" +
                        "&rangeStart={}&rangeEnd={}&from={}&size={} - Event search.",
                users, states, categories, start, end, from, size);

        FindEventAdminParam eventParam = new FindEventAdminParam(users, states, categories, start, end, from, size);
        return eventService.adminFindEvents(eventParam);
    }

    // Редактирование данных события и его статуса (отклонение/публикация).
    @PatchMapping("/{eventId}")
    public EventFullDto adminUpdateEvent(
            @PathVariable long eventId,
            @RequestBody @Valid UpdateEventAdminRequest updateEventAdminRequest
    ) {
        log.info("PATCH /admin/events/{} - " +
                "Editing event data and its status (rejection/publication).", eventId);
        return eventService.adminUpdateEvent(eventId, updateEventAdminRequest);
    }

    private LocalDateTime decodedDateTime(String datetime) {
        if (datetime == null) {
            return null;
        }
        return LocalDateTime.parse(URLDecoder.decode(datetime, StandardCharsets.UTF_8), DATE_TIME_FORMATTER);
    }
}
