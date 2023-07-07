package ru.practicum.mainservice.event;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.mainservice.category.Category;
import ru.practicum.mainservice.category.CategoryRepository;
import ru.practicum.mainservice.event.dto.*;
import ru.practicum.mainservice.exceptions.BadRequestException;
import ru.practicum.mainservice.exceptions.ConflictException;
import ru.practicum.mainservice.exceptions.NotFoundException;
import ru.practicum.mainservice.request.EventRequestRepository;
import ru.practicum.mainservice.request.model.IConfirmedRequests;
import ru.practicum.mainservice.services.StatsService;
import ru.practicum.mainservice.user.User;
import ru.practicum.mainservice.user.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.mainservice.utils.Utils.getPageRequest;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final StatsService statsService;

    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final EventRequestRepository requestRepository;

    @Override
    @Transactional(readOnly = true)
    public List<EventShortDto> initiatorGetEvents(long userId, int from, int size) {

        User initiator = getUser(userId);

        PageRequest pageRequest = getPageRequest(from, size);

        List<Event> events = eventRepository.findEventsByUser(initiator, pageRequest);
        if (events.isEmpty())
            return new ArrayList<>();

        List<Long> eventIds = events.stream()
                .map(Event::getId)
                .collect(Collectors.toList());

        List<IConfirmedRequests> listRequests = requestRepository.findConfirmedRequestCounts(eventIds);

        LocalDateTime start = events.stream().min(Comparator.comparing(Event::getCreatedOn))
                .get().getCreatedOn();

        List<String> uris = eventIds.stream()
                .map(x -> "/events/" + x)
                .collect(Collectors.toList());

        List<ViewStats> stats = statsService.getStatsSearch(start, false, uris);

        return EventMapper.eventToEventShortDto(events, initiator, stats, listRequests);
    }

    @Override
    @Transactional
    public EventFullDto initiatorAddEvent(long userId, NewEventDto newEventDto) {

        // проверяем время
        checkNewEventDate(newEventDto.getEventDate());

        User initiator = getUser(userId);
        Category category = getCategory(newEventDto.getCategory());
        Event newEvent = EventMapper.newEventDtoToEvent(newEventDto, initiator, category);

        try {
            Event createdEvent = eventRepository.save(newEvent);
            return EventMapper.eventToEventFullDto(createdEvent, initiator, 0, 0);
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException("The integrity constraint has been violated.", e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public EventFullDto initiatorGetEvent(long userId, long eventId) {

        User initiator = getUser(userId);

        Event event = getEvent(eventId, initiator);

        return getEventFullDto(event, initiator);
    }

    @Override
    @Transactional
    public EventFullDto initiatorUpdateEvent(long userId, long eventId, UpdateEventUserRequest updateEvent) {

        // TODO: 07.07.2023 maby need bad request somewhere

        User initiator = getUser(userId);

        Event event = getEvent(eventId, initiator);

        // нельзя изменять опубликованное событие
        if (event.getState().equals(EventState.PUBLISHED)) {
            throw new ConflictException("Only pending or canceled events can be changed",
                    "For the requested operation the conditions are not met.");
        }

        if (updateEvent.getCategory() != null) {
            Category category = getCategory(updateEvent.getCategory());
            event.setCategory(category);
        }

        if (updateEvent.getEventDate() != null) {
            // проверяем время
            checkNewEventDate(updateEvent.getEventDate());
            event.setEventDate(updateEvent.getEventDate());
        }

        if (updateEvent.getAnnotation() != null) {
            event.setAnnotation(updateEvent.getAnnotation());
        }

        if (updateEvent.getDescription() != null) {
            event.setDescription(updateEvent.getDescription());
        }

        if (updateEvent.getLocation() != null) {
            event.setLat(updateEvent.getLocation().getLat());
            event.setLon(updateEvent.getLocation().getLon());
        }

        if (updateEvent.getPaid() != null) {
            event.setPaid(updateEvent.getPaid());
        }

        if (updateEvent.getParticipantLimit() != null) {
            event.setParticipantLimit(event.getParticipantLimit());
        }

        if (updateEvent.getRequestModeration() != null) {
            event.setRequestModeration(updateEvent.getRequestModeration());
        }

        if (updateEvent.getTitle() != null) {
            event.setTitle(updateEvent.getTitle());
        }

        if (updateEvent.getStateAction() != null) {
            if (updateEvent.getStateAction().isInGroup(EventStateAction.Group.Admin)) {
                throw new BadRequestException("Field: stateAction. " +
                        "Error: The request to change the event status is specified incorrectly." +
                        "Value must be: SEND_TO_REVIEW, CANCEL_REVIEW");
            }
            if (updateEvent.getStateAction().equals(EventStateAction.CANCEL_REVIEW) &&
                    event.getState().equals(EventState.PENDING)) {
                event.setState(EventState.CANCELED);
            }
            if (updateEvent.getStateAction().equals(EventStateAction.SEND_TO_REVIEW) &&
                    event.getState().equals(EventState.CANCELED)) {
                event.setState(EventState.PENDING);
            }
        }

        try {
            Event updated = eventRepository.save(event);
            return getEventFullDto(updated, initiator);
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException("The integrity constraint has been violated.", e.getMessage());
        }

    }

    private EventFullDto getEventFullDto(Event event, User initiator) {
        IConfirmedRequests confirmedRequest = requestRepository.findConfirmedRequestCount(event.getId());

        long confirmedCount = 0;
        if (confirmedRequest != null) {
            confirmedCount = confirmedRequest.getConfirmedCount();
        }

        LocalDateTime start = event.getCreatedOn();
        List<String> uris = List.of("/events/" + event.getId());

        List<ViewStats> stats = statsService.getStatsSearch(start, false, uris);
        long views = 0;
        if (stats != null && !stats.isEmpty()) {
            views = stats.get(0).getHits();
        }

        return EventMapper.eventToEventFullDto(event, initiator, confirmedCount, views);
    }

    // получение инициатора по id
    private User getUser(long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("User with id=%d not found.",
                        userId)));
    }

    // получение события по id и инициатору
    private Event getEvent(long eventId, User initiator) {
        return eventRepository.findFirstByIdAndUser(eventId, initiator)
                .orElseThrow(() -> new NotFoundException(String.format("Event with id=%d not found.",
                        eventId)));
    }

    // получение категории
    private Category getCategory(long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException(String.format("Category with id=%d not found.",
                        categoryId)));
    }

    /**
     * Дата начала события должна быть не раньше
     * чем через два часа от текущего времени
     *
     * @param newEventDate дата события
     */
    private void checkNewEventDate(LocalDateTime newEventDate) {
        if (!newEventDate.isAfter(LocalDateTime.now().plusHours(2))) {
            throw new BadRequestException("Field: eventDate. Error: The date of the event cannot " +
                    "be earlier than two hours from the current time");
        }
    }
}
