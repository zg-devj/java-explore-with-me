package ru.practicum.mainservice.controllers.individual;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.request.dto.ParticipationRequestDto;

import java.util.List;

/**
 * Закрытый API для работы с запросами текущего пользователя на участие в событиях
 */
@Slf4j
@RestController
@RequestMapping("/users")
public class RequestController {

    // Получение информации о заявках текущего пользователя на участие в чужих событиях
    @GetMapping("/{userId}/requests")
    public List<ParticipationRequestDto> participantGetRequests(
            @PathVariable Long userId
    ) {
        log.info("GET /users/{}/requests - " +
                "Получение информации о заявках текущего пользователя на участие в чужих событиях.", userId);
        return null;
    }

    // Добавление запроса от текущего пользователя на участие в событии
    @PostMapping("/{userId}/requests")
    @ResponseStatus(HttpStatus.CREATED)
    public ParticipationRequestDto participantAddRequest(
            @PathVariable long userId,
            @RequestParam long eventId
    ) {
        log.info("POST /users/{}/requests?eventId={} - " +
                "Добавление запроса от текущего пользователя на участие в событии.", userId, eventId);
        return null;
    }

    // Отмена своего запроса на участие в событии
    @PatchMapping("/{userId}/requests/{requestId}/cancel")
    public ParticipationRequestDto participantCancelRequest(
            @PathVariable String userId,
            @PathVariable String requestId
    ) {
        log.info("PATCH /users/{}/requests/{}/cancel - " +
                "Отмена своего запроса на участие в событии.", userId, requestId);
        return null;
    }

}
