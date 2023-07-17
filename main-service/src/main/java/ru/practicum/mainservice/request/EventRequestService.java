package ru.practicum.mainservice.request;

import ru.practicum.mainservice.request.dto.ParticipationRequestDto;

import java.util.List;

public interface EventRequestService {

    List<ParticipationRequestDto> participantGetRequests(long userId);

    ParticipationRequestDto participantAddRequest(long userId, long eventId);

    ParticipationRequestDto participantCancelRequest(long userId, long requestId);
}
