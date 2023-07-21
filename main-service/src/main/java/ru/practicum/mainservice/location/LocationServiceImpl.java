package ru.practicum.mainservice.location;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.common.ViewStatsDto;
import ru.practicum.mainservice.event.Event;
import ru.practicum.mainservice.event.EventMapper;
import ru.practicum.mainservice.event.LocationMapper;
import ru.practicum.mainservice.event.criteria.EventSearch;
import ru.practicum.mainservice.exceptions.NotFoundException;
import ru.practicum.mainservice.location.criteria.LocationSpecs;
import ru.practicum.mainservice.location.dto.*;
import ru.practicum.mainservice.services.StatsService;
import ru.practicum.mainservice.user.User;
import ru.practicum.mainservice.user.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.mainservice.utils.Util.getPageRequest;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final StatsService statsService;
    private final LocationRepository locationRepository;
    private final UserRepository userRepository;
    private final EventSearch eventSearch;

    // admin

    @Override
    public List<LocationFullDto> adminFindLocations(List<LocationStatus> statuses, int from, int size) {
        PageRequest pageRequest = getPageRequest(from, size);
        List<LocationEntity> locations = locationRepository.findAll(
                LocationSpecs.isLocationStatusIn(statuses),
                pageRequest).getContent();
        return LocationEntityMapper.locationToLocationFullDto(locations);
    }

    @Override
    public LocationDto adminCreateLocation(NewLocationDto newLocationDto) {
        // Добавление опубликованной локации администратором
        return saveLocation(newLocationDto, LocationStatus.PUBLISHED, null);
    }

    @Override
    public LocationDto adminUpdateLocation(long locationId, UpdateLocationRequest locationRequest) {
        LocationEntity location = getLocation(locationId);

        if (locationRequest.getRadius() != null) {
            location.setRadius(locationRequest.getRadius());
        }

        if (locationRequest.getName() != null) {
            location.setName(locationRequest.getName());
        }

        if (locationRequest.getLocation() != null) {
            location.setLat(locationRequest.getLocation().getLat());
            location.setLon(locationRequest.getLocation().getLon());
        }

        // обновляем статус локации
        if (locationRequest.getAction() != null) {
            if (locationRequest.getAction() == LocationStatusAction.PUBLISH_LOCATION) {
                location.setStatus(LocationStatus.PUBLISHED);
            }
            if (locationRequest.getAction() == LocationStatusAction.REJECT_LOCATION) {
                location.setStatus(LocationStatus.REJECTED);
            }
        }

        LocationEntity updated = locationRepository.save(location);
        return LocationEntityMapper.locationToLocationDto(updated);
    }

    // Удаление локации если она REJECTED
    @Override
    @Transactional
    public void adminDeleteLocation(long locationId) {
        locationRepository.deleteByIdAndStatus(locationId, LocationStatus.REJECTED);
    }

    // private

    // Добавление локации пользователем
    // требуется одобрение администратора
    @Override
    public LocationDto initiatorAddLocation(long userId, NewLocationDto newLocationDto) {
        User user = getUser(userId);
        return saveLocation(newLocationDto, LocationStatus.PENDING, user);
    }

    // public

    // Получение локаций
    @Override
    public List<LocationDto> findAllLocations(int from, int size) {
        PageRequest pageRequest = getPageRequest(from, size);
        List<LocationEntity> locations = locationRepository.findAllByStatus(LocationStatus.PUBLISHED, pageRequest);
        return LocationEntityMapper.locationToLocationDto(locations);
    }

    // Получение опубликованных событий в локации
    @Override
    public LocationEventsDto findEventsInLocation(long locationId, int from, int size) {
        LocationEntity location = getLocationPublished(locationId);

        PageRequest pageRequest = getPageRequest(from, size);

        // Получаем список событий в области действия
        // локации в период между start и end
        List<Event> events = eventSearch.findEventsInLocation(location.getLat(),
                location.getLon(), location.getRadius(), pageRequest);


        LocationEventsDto result = new LocationEventsDto();
        result.setId(location.getId());
        result.setName(location.getName());
        result.setRadius(location.getRadius());
        result.setLocation(LocationMapper.coordsToLocation(location.getLat(), location.getLon()));

        if (events.isEmpty()) {
            // если список пуст
            result.setEvents(new ArrayList<>());
        } else {
            // получаем события
            LocalDateTime start = events.stream()
                    .min(Comparator.comparing(Event::getCreatedOn))
                    .get().getCreatedOn();
            LocalDateTime end = LocalDateTime.now().plusHours(1);
            List<ViewStatsDto> stats = getStats(events, start, end);
            result.setEvents(EventMapper.eventToEventShortDto(events, stats));
        }

        return result;
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

    // Добавление локации
    private LocationDto saveLocation(NewLocationDto newLocationDto, LocationStatus status, User owner) {
        LocationEntity newLocation = LocationEntityMapper.newLocationDtoToLocation(newLocationDto,
                status, owner);
        LocationEntity saved = locationRepository.save(newLocation);
        return LocationEntityMapper.locationToLocationDto(saved);
    }

    // Получение инициатора по id
    private User getUser(long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("User with id=%d not found.",
                        userId)));
    }

    // Получение локации по id
    private LocationEntity getLocation(long locationId) {
        return locationRepository.findById(locationId)
                .orElseThrow(() -> new NotFoundException(String.format("Location with id=%d not found.",
                        locationId)));
    }

    // Получение опубликованных локации по id
    private LocationEntity getLocationPublished(long locationId) {
        return locationRepository.findByIdAndStatus(locationId, LocationStatus.PUBLISHED)
                .orElseThrow(() -> new NotFoundException(String.format("Location with id=%d not found.",
                        locationId)));
    }
}
