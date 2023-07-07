package ru.practicum.mainservice.request.dto;

import lombok.*;
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
    private Long requestor;

    // Статус заявки
    private RequestStatus status;
}
