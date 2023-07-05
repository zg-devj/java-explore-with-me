package ru.practicum.mainservice.event.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.event.dto.EventFullDto;
import ru.practicum.mainservice.event.dto.EventShortDto;
import ru.practicum.mainservice.event.dto.NewEventDto;
import ru.practicum.mainservice.event.dto.UpdateEventUserRequest;
import ru.practicum.mainservice.request.dto.EventRequestStatusUpdateRequest;
import ru.practicum.mainservice.request.dto.EventRequestStatusUpdateResult;
import ru.practicum.mainservice.request.dto.ParticipationRequestDto;

import javax.validation.constraints.PositiveOrZero;
import java.util.List;

/**
 * Закрытый API для работы с событиями
 */
@Slf4j
@RestController
@RequestMapping("/users")
public class PrivateEventController {

    // Получение событий, добавленных текущим пользователем
    @GetMapping("/{userId}/events")
    public List<EventShortDto> initiatorGetEvents(
            @PathVariable long userId,
            @RequestParam(required = false, defaultValue = "0") @PositiveOrZero int from,
            @RequestParam(required = false, defaultValue = "10") @PositiveOrZero int size
    ) {
        log.info("GET /users/{}/events - Получение событий, добавленных текущим пользователем.", userId);
        return null;
    }

    // Добавление нового события
    @PostMapping("/{userId}/events")
    @ResponseStatus(HttpStatus.CREATED)
    public EventShortDto initiatorAddEvent(
            @PathVariable long userId,
            @RequestBody NewEventDto newEventDto
    ) {
        log.info("POST /users/{}/events - Добавление нового события.", userId);
        return null;
    }

    // Получение полной информации о событии добавленном текущим пользователем
    @GetMapping("/{userId}/events/{eventId}")
    public EventFullDto initiatorGetEvent(
            @PathVariable long userId,
            @PathVariable long eventId
    ) {
        log.info("GET /users/{}/events/{} - " +
                        "Получение полной информации о событии добавленном текущим пользователем.",
                userId, eventId);
        return null;
    }

    // Изменение события добавленного текущим пользователем
    @PatchMapping("/{userId}/events/{eventId}")
    public EventFullDto initiatorUpdateEvent(
            @PathVariable long userId,
            @PathVariable long eventId,
            @RequestBody UpdateEventUserRequest updateEventUserRequest
    ) {
        log.info("PATCH /users/{}/events/{} - Изменение события добавленного текущим пользователем.",
                userId, eventId);
        return null;
    }

    // Получение информации о запросах на участие в событии текущего пользователя
    @GetMapping("/{userId}/events/{eventId}/requests")
    public List<ParticipationRequestDto> initiatorGetEventRequests(
            @PathVariable long userId,
            @PathVariable long eventId
    ) {
        log.info("GET /users/{}/events/{}/requests - " +
                        "Получение информации о запросах на участие в событии текущего пользователя.",
                userId, eventId);
        return null;
    }

    // Изменение статуса (подтверждена, отменена) заявок на участие
    // в событии текущего пользователя
    @PatchMapping("/{userId}/events/{eventId}/requests")
    public EventRequestStatusUpdateResult initiatorChangeRequestStatus(
            @PathVariable long userId,
            @PathVariable long eventId,
            @RequestBody EventRequestStatusUpdateRequest updateRequest
    ) {
        log.info("PATCH /users/{}/events/{}/requests - " +
                        "Изменение статуса (подтверждена, отменена) заявок на участие " +
                        "в событии текущего пользователя.", userId, eventId);
        return null;
    }
}
