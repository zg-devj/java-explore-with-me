package ru.practicum.mainservice.location;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.mainservice.exceptions.NotFoundException;
import ru.practicum.mainservice.location.dto.LocationDto;
import ru.practicum.mainservice.location.dto.NewLocationDto;
import ru.practicum.mainservice.location.dto.UpdateLocationRequest;
import ru.practicum.mainservice.user.User;
import ru.practicum.mainservice.user.UserRepository;

import java.util.List;

import static ru.practicum.mainservice.utils.Util.getPageRequest;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;
    private final UserRepository userRepository;

    // admin

    @Override
    public List<LocationDto> adminFindLocations(List<LocationStatus> status, int from, int size) {
        PageRequest pageRequest = getPageRequest(from, size);
        List<LocationEntity> locations = locationRepository.findAll(pageRequest).getContent();
        return null;
    }

    @Override
    public LocationDto adminCreateLocation(NewLocationDto newLocationDto) {
        // Добавление опубликованной локации администратором
        return getLocationDto(newLocationDto, LocationStatus.PUBLISHED, null);
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
        return LocationMapper.locationToLocationDto(updated);
    }

    // Удаление локации если она REJECTED
    @Override
    @Transactional
    public void adminDeleteLocation(long locationId) {
        locationRepository.deleteByIdAndStatus(locationId, LocationStatus.REJECTED);
    }

    // private

    // Добавление локации пользователем
    @Override
    public LocationDto initiatorAddLocation(long userId, NewLocationDto newLocationDto) {
        User user = getUser(userId);
        return getLocationDto(newLocationDto, LocationStatus.PENDING, user);
    }

    // Добавление локации
    private LocationDto getLocationDto(NewLocationDto newLocationDto, LocationStatus status, User owner) {
        LocationEntity newLocation = LocationMapper.newLocationDtoToLocation(newLocationDto,
                status, owner);
        LocationEntity saved = locationRepository.save(newLocation);
        return LocationMapper.locationToLocationDto(saved);
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
}
