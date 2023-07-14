package ru.practicum.mainservice.request;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.mainservice.event.Event;
import ru.practicum.mainservice.event.EventRepository;
import ru.practicum.mainservice.event.EventState;
import ru.practicum.mainservice.exceptions.ConflictException;
import ru.practicum.mainservice.exceptions.NotFoundException;
import ru.practicum.mainservice.request.dto.ParticipationRequestDto;
import ru.practicum.mainservice.user.User;
import ru.practicum.mainservice.user.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class EventRequestServiceImpl implements EventRequestService {

    private final EventRequestRepository requestRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ParticipationRequestDto> participantGetRequests(long userId) {
        User requester = getUserCheck(userId);

        List<EventRequest> requestList = requestRepository.findAllByRequester(requester);
        return EventRequestMapper.eventRequestToParticipationRequestDto(requestList);
    }

    @Override
//    @Transactional
    public ParticipationRequestDto participantAddRequest(long userId, long eventId) {
        User requestor = getUserCheck(userId);
        Event event = getEventCheck(eventId);

        long confirmedCount = event.getConfirmedRequests();


        // если у события достигнут лимит запросов на участие
        if (event.getParticipantLimit() != 0 &&
                event.getParticipantLimit() <= confirmedCount) {
            throw new ConflictException("The participant limit has been reachedThe participant limit has been reached",
                    "For the requested operation the conditions are not met.");
        }

        // нельзя участвовать в неопубликованном событии
        if (!event.getState().equals(EventState.PUBLISHED)) {
            throw new ConflictException("You cannot participate in an unpublished event.",
                    "Integrity constraint has been violated.");
        }

        // инициатор события не может добавить запрос на участие в своём событии
        if (Objects.equals(event.getUser().getId(), requestor.getId())) {
            throw new ConflictException("The initiator of the event cannot add a request to participate in his event.",
                    "Integrity constraint has been violated.");
        }

        // если для события отключена пре-модерация запросов на участие,
        // то запрос должен автоматически перейти в состояние подтвержденного
        RequestStatus requestStatus = RequestStatus.PENDING;
        if (!event.getRequestModeration() || event.getParticipantLimit() == 0) {
            requestStatus = RequestStatus.CONFIRMED;
            event.setConfirmedRequests(++confirmedCount);
        }

        EventRequest eventRequest = EventRequest.builder()
                .created(LocalDateTime.now())
                .event(event)
                .requester(requestor)
                .status(requestStatus)
                .build();

        try {

            EventRequest saved = requestRepository.save(eventRequest);
            ParticipationRequestDto requestDto = EventRequestMapper.eventRequestToParticipationRequestDto(saved);
            return requestDto;
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException("The integrity constraint has been violated.", e.getMessage());
        }

    }

    @Override
    @Transactional
    public ParticipationRequestDto participantCancelRequest(long userId, long requestId) {
        EventRequest eventRequest = requestRepository.findFirstByIdAndRequesterId(requestId, userId)
                .orElseThrow(() -> new NotFoundException(String.format("Request with id=%d not found.",
                        userId)));

        eventRequest.setStatus(RequestStatus.CANCELED);

        EventRequest updated = requestRepository.save(eventRequest);
        return EventRequestMapper.eventRequestToParticipationRequestDto(updated);
    }

    // получение инициатора по id
    private User getUserCheck(long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("User with id=%d not found.",
                        userId)));
    }

    // получение события по id и инициатору
    private Event getEventCheck(long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException(String.format("Event with id=%d not found.",
                        eventId)));
    }
}
