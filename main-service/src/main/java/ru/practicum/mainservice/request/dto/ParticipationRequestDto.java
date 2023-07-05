package ru.practicum.mainservice.request.dto;

import ru.practicum.mainservice.request.RequestStatus;

/**
 * Заявка на участие в событии
 */
public class ParticipationRequestDto {
    // Дата и время создания заявки
    private String created;

    // Идентификатор события
    private Long event;

    // Идентификатор заявки
    private Long id;

    // Идентификатор пользователя, отправившего заявку
    private Long requestor;

    // Статус заявки
    private RequestStatus status;
}
