package ru.practicum.mainservice.request.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.mainservice.request.RequestStatus;

import java.time.LocalDateTime;

/**
 * Заявка на участие в событии
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ParticipationRequestDto {
    // Дата и время создания заявки
    private LocalDateTime created;

    // Идентификатор события
    private Long event;

    // Идентификатор заявки
    private Long id;

    // Идентификатор пользователя, отправившего заявку
    private Long requester;

    // Статус заявки
    private RequestStatus status;
}
