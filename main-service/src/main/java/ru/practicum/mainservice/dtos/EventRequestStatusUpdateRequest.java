package ru.practicum.mainservice.dtos;

import java.util.List;

// Результат подтверждения/отклонения заявок на участие в событии
public class EventRequestStatusUpdateRequest {
    private List<ParticipationRequestDto> confirmedRequests;
    private List<ParticipationRequestDto> rejectedRequests;
}
