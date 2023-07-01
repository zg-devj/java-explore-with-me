package ru.practicum.mainservice.dtos;

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
    private RequestState status;
}
