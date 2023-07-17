package ru.practicum.mainservice.request;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.mainservice.request.dto.ParticipationRequestDto;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EventRequestMapper {
    public static ParticipationRequestDto eventRequestToParticipationRequestDto(EventRequest eventRequest) {
        ParticipationRequestDto dto = new ParticipationRequestDto();
        dto.setId(eventRequest.getId());
        dto.setCreated(eventRequest.getCreated());
        dto.setEvent(eventRequest.getEvent().getId());
        dto.setRequester(eventRequest.getRequester().getId());
        dto.setStatus(eventRequest.getStatus());

        return dto;
    }

    public static List<ParticipationRequestDto> eventRequestToParticipationRequestDto(List<EventRequest> requestList) {
        return requestList.stream()
                .map(EventRequestMapper::eventRequestToParticipationRequestDto)
                .collect(Collectors.toList());
    }
}
