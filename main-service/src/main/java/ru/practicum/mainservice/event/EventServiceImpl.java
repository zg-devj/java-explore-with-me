package ru.practicum.mainservice.event;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.common.ViewStatsDto;
import ru.practicum.mainservice.category.Category;
import ru.practicum.mainservice.category.CategoryRepository;
import ru.practicum.mainservice.event.dto.*;
import ru.practicum.mainservice.event.dto.params.FindEventAdminParam;
import ru.practicum.mainservice.event.dto.params.FindEventPublicParam;
import ru.practicum.mainservice.exceptions.BadRequestException;
import ru.practicum.mainservice.exceptions.ConflictException;
import ru.practicum.mainservice.exceptions.NotFoundException;
import ru.practicum.mainservice.request.EventRequest;
import ru.practicum.mainservice.request.EventRequestMapper;
import ru.practicum.mainservice.request.EventRequestRepository;
import ru.practicum.mainservice.request.RequestStatus;
import ru.practicum.mainservice.request.dto.ParticipationRequestDto;
import ru.practicum.mainservice.services.StatsService;
import ru.practicum.mainservice.user.User;
import ru.practicum.mainservice.user.UserMapper;
import ru.practicum.mainservice.user.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.mainservice.event.criteria.EventSpecs.*;
import static ru.practicum.mainservice.utils.Util.getPageRequest;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final StatsService statsService;

    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final EventRequestRepository requestRepository;

    // private

    @Override
    public List<EventShortDto> initiatorGetEvents(long userId, int from, int size) {

        User initiator = getUser(userId);

        PageRequest pageRequest = getPageRequest(from, size);

        List<Event> events = eventRepository.findEventsByUser(initiator, pageRequest);
        if (events.isEmpty())
            return new ArrayList<>();

        List<Long> eventIds = events.stream()
                .map(Event::getId)
                .collect(Collectors.toList());

        LocalDateTime start = events.stream().min(Comparator.comparing(Event::getCreatedOn))
                .get().getCreatedOn();

        List<String> uris = eventIds.stream()
                .map(x -> "/events/" + x)
                .collect(Collectors.toList());

        List<ViewStatsDto> stats = statsService.getStatsSearchInterval(start, false, uris);

        return EventMapper.eventToEventShortDto(events, stats, UserMapper.userToUserShortDto(initiator));
    }

    @Override
    @Transactional
    public EventFullDto initiatorAddEvent(long userId, NewEventDto newEventDto) {

        // проверяем время
        checkNewEventDate(newEventDto.getEventDate(), 2);

        User initiator = getUser(userId);
        Category category = getCategory(newEventDto.getCategory());
        Event newEvent = EventMapper.newEventDtoToEvent(newEventDto, initiator, category);

        try {
            Event createdEvent = eventRepository.save(newEvent);
            return EventMapper.eventToEventFullDto(createdEvent, UserMapper.userToUserShortDto(initiator), 0);
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException("The integrity constraint has been violated.", e.getMessage());
        }
    }


    @Override
    public EventFullDto initiatorGetEvent(long userId, long eventId) {

        Event event = getEvent(eventId, userId);

        return getEventFullDto(event, event.getUser());
    }

    @Override
    @Transactional
    public EventFullDto initiatorUpdateEvent(long userId, long eventId, UpdateEventUserRequest updateEvent) {

        Event event = getEvent(eventId, userId);

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
            checkNewEventDate(updateEvent.getEventDate(), 2);
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
            event.setParticipantLimit(updateEvent.getParticipantLimit());
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
            return getEventFullDto(updated, event.getUser());
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException("The integrity constraint has been violated.", e.getMessage());
        }

    }

    @Override
    public List<ParticipationRequestDto> initiatorGetEventRequests(long userId, long eventId) {
        // получаем событие инициатора
        Event event = getEvent(eventId, userId);
        // получаем все заявки на событие инициатора
        List<EventRequest> requests = requestRepository.findAllByEventId(event.getId());

        return EventRequestMapper.eventRequestToParticipationRequestDto(requests);
    }

    @Override
    public EventRequestStatusUpdateResult initiatorChangeRequestStatus(
            long userId, long eventId, EventRequestStatusUpdateRequest updateRequest) {

        Event event = getEvent(eventId, userId);

        if (updateRequest.getStatus() == RequestStatus.PENDING) {
            // статус нельзя установить в PENDING
            throw new BadRequestException("The status can only be CONFIRMED, REJECTED");
        }

        // если для события лимит заявок равен 0 или отключена
        // пре-модерация заявок, то подтверждение заявок не требуется
        if ((!event.getRequestModeration() || event.getParticipantLimit() == 0) &&
                updateRequest.getStatus() == RequestStatus.CONFIRMED) {
            throw new BadRequestException("The event does not need confirmation of requests");
        }

        // получаем заявки
        List<EventRequest> allById = requestRepository.findAllById(updateRequest.getRequestIds());

        // если заявки на подтверждения
        if (updateRequest.getStatus() == RequestStatus.CONFIRMED) {
            confirmEventRequest(eventId, event, allById);
        }

        // если заявки на отклонения
        if (updateRequest.getStatus() == RequestStatus.REJECTED) {
            rejectEventRequest(eventId, allById);
        }

        return getUpdateResult(event);
    }

    private EventRequestStatusUpdateResult getUpdateResult(Event event) {
        // возвращаем ответ все запросы со статусом не PENDING
        List<EventRequest> resultRequests = requestRepository.findAllByEventIdAndStatusNot(event.getId(), RequestStatus.PENDING);
        // разбиваем по двум спискам
        EventRequestStatusUpdateResult updateResult = new EventRequestStatusUpdateResult();
        List<ParticipationRequestDto> confirmed = EventRequestMapper.eventRequestToParticipationRequestDto(
                resultRequests.stream()
                        .filter(f -> f.getStatus() == RequestStatus.CONFIRMED)
                        .collect(Collectors.toList()));
        List<ParticipationRequestDto> rejected = EventRequestMapper.eventRequestToParticipationRequestDto(
                resultRequests.stream()
                        .filter(f -> f.getStatus() == RequestStatus.REJECTED)
                        .collect(Collectors.toList()));
        updateResult.setConfirmedRequests(confirmed);
        updateResult.setRejectedRequests(rejected);

        return updateResult;
    }

    private void rejectEventRequest(long eventId, List<EventRequest> allById) {
        for (EventRequest eventRequest : allById) {
            if (eventRequest.getStatus() == RequestStatus.PENDING &&
                    eventRequest.getEvent().getId() == eventId) {
                // если статус PENDING
                eventRequest.setStatus(RequestStatus.REJECTED);
            } else {
                // если нет
                throw new ConflictException("The status can be changed only for applications " +
                        "that are in the waiting state.", "The integrity constraint on the change is broken.");
            }
        }
        // изменяем заявки
        requestRepository.saveAll(allById);
    }

    private void confirmEventRequest(long eventId, Event event, List<EventRequest> allById) {
        long confirmedCount = event.getConfirmedRequests();

        // превышен лимит
        if (event.getParticipantLimit() <= confirmedCount) {
            throw new ConflictException("The participant limit has been reached",
                    "For the requested operation the conditions are not met.");
        }

        boolean isMoreLimit = false;
        for (EventRequest eventRequest : allById) {
            if (eventRequest.getStatus() == RequestStatus.PENDING &&
                    eventRequest.getEvent().getId() == eventId) {
                if (event.getParticipantLimit() > confirmedCount) {
                    // изменяем статус на CONFIRMED
                    eventRequest.setStatus(RequestStatus.CONFIRMED);
                    ++confirmedCount;
                    event.setConfirmedRequests(confirmedCount);
                } else {
                    isMoreLimit = true;
                    // лимит превышен, изменяем на CANCELLED
                    eventRequest.setStatus(RequestStatus.CANCELED);
                }
            } else {
                // если нет
                throw new ConflictException("The status can be changed only for applications " +
                        "that are in the waiting state.", "The integrity constraint on the change is broken.");
            }
        }

        // изменяем заявки
        eventRepository.save(event);
        requestRepository.saveAll(allById);

        if (isMoreLimit) {
            // при достижении лимита, заявки со статусом PENDING
            // переходят в статус CANCELED
            requestRepository.updateAllByEventIdAndStatus(eventId, RequestStatus.PENDING);
        }
    }

    // admin

    @Override
    public List<EventFullDto> adminFindEvents(FindEventAdminParam param) {

        PageRequest pageRequest = getPageRequest(param.getFrom(), param.getSize());

        List<Event> events = eventRepository.findAll(isUsersIn(param.getUsers())
                        .and(isCategoriesIn(param.getCategories()))
                        .and(isStatesIn(param.getStates()))
                        .and(greaterDate(param.getRangeStart()))
                        .and(lessDate(param.getRangeEnd())),
                pageRequest).getContent();

        // если список пуст
        if (events.isEmpty()) {
            return new ArrayList<>();
        }

        List<ViewStatsDto> stats = getStats(events, param.getRangeStart(), param.getRangeEnd());

        // возвращаем результат
        return EventMapper.eventToEventFullDto(events, stats);
    }

    @Override
    @Transactional
    public EventFullDto adminUpdateEvent(long eventId, UpdateEventAdminRequest updateEvent) {

        Event event = getEvent(eventId);

        User initiator = event.getUser();

        // событие можно публиковать, только если оно в состоянии
        // ожидания публикации
        if (updateEvent.getStateAction() == EventStateAction.PUBLISH_EVENT &&
                event.getState() != EventState.PENDING) {
            throw new ConflictException("An event can be published only if it is in the publication " +
                    "waiting state.", "The event does not meet the editing rules.");
        }

        // событие можно отклонить, только если оно еще не опубликовано
        if (updateEvent.getStateAction() == EventStateAction.REJECT_EVENT &&
                event.getState() == EventState.PUBLISHED) {
            throw new ConflictException("An event can be rejected only if it has not been published yet",
                    "The event does not meet the editing rules.");
        }

        if (updateEvent.getEventDate() != null) {
            checkNewEventDate(updateEvent.getEventDate(), 1);
        }

        if (updateEvent.getAnnotation() != null) {
            event.setAnnotation(updateEvent.getAnnotation());
        }

        if (updateEvent.getCategory() != null) {
            Category newCategory = getCategory(updateEvent.getCategory());
            event.setCategory(newCategory);
        }

        if (updateEvent.getDescription() != null) {
            event.setDescription(updateEvent.getDescription());
        }

        if (updateEvent.getEventDate() != null) {
            event.setEventDate(updateEvent.getEventDate());
        }

        if (updateEvent.getLocation() != null) {
            event.setLat(updateEvent.getLocation().getLat());
            event.setLon(updateEvent.getLocation().getLon());
        }

        if (updateEvent.getPaid() != null) {
            event.setPaid(updateEvent.getPaid());
        }

        if (updateEvent.getParticipantLimit() != null) {
            event.setParticipantLimit(updateEvent.getParticipantLimit());
        }

        if (updateEvent.getRequestModeration() != null) {
            event.setRequestModeration(updateEvent.getRequestModeration());
        }

        if (updateEvent.getTitle() != null) {
            event.setTitle(updateEvent.getTitle());
        }

        if (updateEvent.getStateAction() != null) {
            if (updateEvent.getStateAction().isInGroup(EventStateAction.Group.Initiator)) {
                throw new BadRequestException("Field: stateAction. " +
                        "Error: The request to change the event status is specified incorrectly." +
                        "Value must be: PUBLISH_EVENT, REJECT_EVENT");
            }
            if (updateEvent.getStateAction().equals(EventStateAction.REJECT_EVENT)) {
                event.setState(EventState.CANCELED);
            }
            if (updateEvent.getStateAction().equals(EventStateAction.PUBLISH_EVENT)) {
                event.setState(EventState.PUBLISHED);
                event.setPublishedOn(LocalDateTime.now());
            }
        }

        try {
            Event updated = eventRepository.save(event);
            return getEventFullDto(updated, initiator);
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException("The integrity constraint has been violated.", e.getMessage());
        }
    }

    // public

    @Override
    public List<EventShortDto> publicFindEvents(FindEventPublicParam param) {

        Sort sort = Sort.by(Sort.Direction.DESC, "eventDate");
        if (param.getSort() == EventSort.VIEWS) {
            sort = Sort.by("views").descending();
        }

        PageRequest pageRequest = getPageRequest(param.getFrom(), param.getSize(), sort);

        List<Event> events = eventRepository.findAll(
                isTextAnnotation(param.getText()).or(isTextDescription(param.getText()))
                        .and(greaterDate(param.getRangeStart()))
                        .and(lessDate(param.getRangeEnd()))
                        .and(isCategoriesIn(param.getCategories()))
                        .and(isPaid(param.getPaid()))
                        .and(isAvailable(param.isOnlyAvailable())),
                pageRequest).getContent();

        // если список пуст
        if (events.isEmpty()) {
            return new ArrayList<>();
        }

        List<ViewStatsDto> stats = getStats(events, param.getRangeStart(), param.getRangeEnd());

        List<EventShortDto> list = EventMapper.eventToEventShortDto(events, stats);

        if (param.getSort() == EventSort.VIEWS) {
            Collections.sort(list, Comparator.comparing(EventShortDto::getViews));
        }
        return list;
    }

    private List<ViewStatsDto> getStats(List<Event> events, LocalDateTime rangeStart, LocalDateTime rangeEnd) {
        List<Long> eventIds = events.stream()
                .map(Event::getId)
                .collect(Collectors.toList());

        LocalDateTime start = rangeStart != null ?
                rangeStart : events.stream()
                .min(Comparator.comparing(Event::getCreatedOn))
                .get().getCreatedOn();

        LocalDateTime end = rangeEnd != null ?
                rangeEnd : LocalDateTime.now();

        List<String> uris = eventIds.stream()
                .map(x -> "/events/" + x)
                .collect(Collectors.toList());

        return statsService.getStatsSearch(start, end, false, uris);
    }

    @Override
    public EventFullDto publicGetEvent(long eventId) {
        Event event = eventRepository.findFirstByIdAndState(eventId, EventState.PUBLISHED)
                .orElseThrow(() -> new NotFoundException(String.format("Event with id=%d not found.",
                        eventId)));

        return getEventFullDto(event, event.getUser(), true);
    }

    private EventFullDto getEventFullDto(Event event, User initiator) {
        return getEventFullDto(event, initiator, false);
    }

    private EventFullDto getEventFullDto(Event event, User initiator, boolean unique) {

        LocalDateTime start = event.getCreatedOn();
        List<String> uris = List.of("/events/" + event.getId());

        List<ViewStatsDto> stats = statsService.getStatsSearchInterval(start, unique, uris);
        long views = 0;
        if (stats != null && !stats.isEmpty()) {
            views = stats.get(0).getHits();
        }

        return EventMapper.eventToEventFullDto(event, UserMapper.userToUserShortDto(initiator), views);
    }

    // получение инициатора по id
    private User getUser(long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("User with id=%d not found.",
                        userId)));
    }

    private Event getEvent(long eventId, long userId) {
        return eventRepository.findFirstByIdAndUserId(eventId, userId)
                .orElseThrow(() -> new NotFoundException(String.format("Event with id=%d not found.",
                        eventId)));
    }

    // получение события по id и инициатору
    private Event getEvent(long eventId) {
        return eventRepository.findById(eventId)
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
     * @param afterHour    не раньше чем часов от текущего времени
     */
    private void checkNewEventDate(LocalDateTime newEventDate, long afterHour) {
        if (!newEventDate.isAfter(LocalDateTime.now().plusHours(afterHour))) {
            throw new BadRequestException("Field: eventDate. Error: The date of the event cannot " +
                    "be earlier than two hours from the current time");
        }
    }
}
