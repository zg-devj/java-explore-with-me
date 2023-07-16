package ru.practicum.mainservice.event;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.common.ViewStatsDto;
import ru.practicum.mainservice.category.Category;
import ru.practicum.mainservice.category.CategoryMapper;
import ru.practicum.mainservice.event.dto.EventFullDto;
import ru.practicum.mainservice.event.dto.EventShortDto;
import ru.practicum.mainservice.event.dto.Location;
import ru.practicum.mainservice.event.dto.NewEventDto;
import ru.practicum.mainservice.user.User;
import ru.practicum.mainservice.user.UserMapper;
import ru.practicum.mainservice.user.dto.UserShortDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EventMapper {
    public static Event newEventDtoToEvent(NewEventDto newEventDto, User user, Category category) {

        // Set default value
        if (newEventDto.getPaid() == null) {
            newEventDto.setPaid(false);
        }
        if (newEventDto.getParticipantLimit() == null) {
            newEventDto.setParticipantLimit(0);
        }
        if (newEventDto.getRequestModeration() == null) {
            newEventDto.setRequestModeration(true);
        }

        return Event.builder()
                .user(user)
                .annotation(newEventDto.getAnnotation())
                .category(category)
                .description(newEventDto.getDescription())
                .eventDate(newEventDto.getEventDate())
                .lat(newEventDto.getLocation().getLat())
                .lon(newEventDto.getLocation().getLon())
                .paid(newEventDto.getPaid())
                .participantLimit(newEventDto.getParticipantLimit())
                .requestModeration(newEventDto.getRequestModeration())
                .title(newEventDto.getTitle())
                .confirmedRequests(0L)
                .state(EventState.PENDING)
                .createdOn(LocalDateTime.now())
                .build();
    }

    public static EventFullDto eventToEventFullDto(Event event, UserShortDto userShortDto, long views) {
        Location location = Location.builder()
                .lat(event.getLat())
                .lon(event.getLon()).build();

        EventFullDto eventFullDto = new EventFullDto();
        eventFullDto.setAnnotation(event.getAnnotation());
        eventFullDto.setCategory(CategoryMapper.categoryToCategoryDto(event.getCategory()));
        eventFullDto.setConfirmedRequests(event.getConfirmedRequests());
        eventFullDto.setCreatedOn(event.getCreatedOn());
        eventFullDto.setDescription(event.getDescription());
        eventFullDto.setEventDate(event.getEventDate());
        eventFullDto.setId(event.getId());
        eventFullDto.setInitiator(userShortDto);
        eventFullDto.setLocation(location);
        eventFullDto.setPaid(event.getPaid());
        eventFullDto.setParticipantLimit(event.getParticipantLimit());
        eventFullDto.setPublishedOn(event.getPublishedOn());
        eventFullDto.setRequestModeration(event.getRequestModeration());
        eventFullDto.setState(event.getState());
        eventFullDto.setTitle(event.getTitle());
        eventFullDto.setViews(views);

        return eventFullDto;
    }

    public static EventShortDto eventToEventShortDto(Event event, long views, UserShortDto initiator) {
        EventShortDto eventShortDto = new EventShortDto();
        eventShortDto.setAnnotation(event.getAnnotation());
        eventShortDto.setCategory(CategoryMapper.categoryToCategoryDto(event.getCategory()));
        eventShortDto.setConfirmedRequests(event.getConfirmedRequests());
        eventShortDto.setEventDate(event.getEventDate());
        eventShortDto.setId(event.getId());
        eventShortDto.setInitiator(initiator);
        eventShortDto.setPaid(event.getPaid());
        eventShortDto.setTitle(event.getTitle());
        eventShortDto.setViews(views);

        return eventShortDto;
    }

    public static EventShortDto eventToEventShortDto(Event event, long views) {
        EventShortDto eventShortDto = new EventShortDto();
        eventShortDto.setAnnotation(event.getAnnotation());
        eventShortDto.setCategory(CategoryMapper.categoryToCategoryDto(event.getCategory()));
        eventShortDto.setConfirmedRequests(event.getConfirmedRequests());
        eventShortDto.setEventDate(event.getEventDate());
        eventShortDto.setId(event.getId());
        eventShortDto.setInitiator(UserMapper.userToUserShortDto(event.getUser()));
        eventShortDto.setPaid(event.getPaid());
        eventShortDto.setTitle(event.getTitle());
        eventShortDto.setViews(views);

        return eventShortDto;
    }

    public static List<EventFullDto> eventToEventFullDto(List<Event> events, List<ViewStatsDto> stats) {

        List<EventFullDto> list = new ArrayList<>();
        for (Event event : events) {
            long views = 0L;
            if (stats != null && !stats.isEmpty()) {
                views = stats.stream()
                        .filter(f -> Objects.equals(f.getUri(), "/events/" + event.getId()))
                        .findFirst()
                        .map(ViewStatsDto::getHits).orElse(0L);
            }
            list.add(EventMapper.eventToEventFullDto(event, UserMapper.userToUserShortDto(event.getUser()), views));
        }
        return list;
    }

    public static List<EventShortDto> eventToEventShortDto(List<Event> events, List<ViewStatsDto> stats,
                                                           UserShortDto userShortDto) {
        List<EventShortDto> list = new ArrayList<>();
        for (Event event : events) {
            long views = 0L;
            if (stats != null && !stats.isEmpty()) {
                views = stats.stream()
                        .filter(f -> Objects.equals(f.getUri(), "/events/" + event.getId()))
                        .findFirst()
                        .map(ViewStatsDto::getHits).orElse(0L);
            }
            list.add(EventMapper.eventToEventShortDto(event, views, userShortDto));
        }
        return list;
    }

    public static List<EventShortDto> eventToEventShortDto(List<Event> events, List<ViewStatsDto> stats) {
        List<EventShortDto> list = new ArrayList<>();
        for (Event event : events) {
            long views = 0L;
            if (stats != null && !stats.isEmpty()) {
                views = stats.stream()
                        .filter(f -> Objects.equals(f.getUri(), "/events/" + event.getId()))
                        .findFirst()
                        .map(ViewStatsDto::getHits).orElse(0L);
            }
            list.add(EventMapper.eventToEventShortDto(event, views));
        }
        return list;
    }
}
