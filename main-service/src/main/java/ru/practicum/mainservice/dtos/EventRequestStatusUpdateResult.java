package ru.practicum.mainservice.dtos;

import java.util.List;

/**
 * Изменение статуса запроса на участие в событии текущего пользователя
 */
public class EventRequestStatusUpdateResult {
    // Идентификаторы запросов на участие в событии текущего пользователя
    private List<Long> requestIds;

    // Новый статус запроса на участие в событии текущего пользователя
    // CONFIRMED, REJECTED
    private RequestState status;
}
