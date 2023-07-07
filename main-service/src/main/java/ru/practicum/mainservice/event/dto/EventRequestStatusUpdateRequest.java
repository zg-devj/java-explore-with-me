package ru.practicum.mainservice.event.dto;

import ru.practicum.mainservice.request.RequestStatus;

import java.util.List;

/**
 * Изменение статуса запроса на участие в событии текущего пользователя
 */
public class EventRequestStatusUpdateRequest {
    // Идентификаторы запросов на участие в событии текущего пользователя
    private List<Long> requestIds;

    // Новый статус запроса на участие в событии текущего пользователя
    // CONFIRMED, REJECTED
    private RequestStatus status;
}
