package ru.practicum.mainservice.event;

import ru.practicum.mainservice.event.dto.EventFullDto;
import ru.practicum.mainservice.event.dto.EventShortDto;
import ru.practicum.mainservice.event.dto.NewEventDto;
import ru.practicum.mainservice.event.dto.UpdateEventUserRequest;

import java.util.List;

public interface EventService {
    // private
    List<EventShortDto> initiatorGetEvents(long userId, int from, int size);

    EventFullDto initiatorAddEvent(long userId, NewEventDto newEventDto);

    EventFullDto initiatorGetEvent(long userId, long eventId);

    EventFullDto initiatorUpdateEvent(long userId, long eventId,
                                      UpdateEventUserRequest updateEventUserRequest);
}
