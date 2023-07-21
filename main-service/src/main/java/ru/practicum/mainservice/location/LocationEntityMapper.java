package ru.practicum.mainservice.location;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.mainservice.event.LocationMapper;
import ru.practicum.mainservice.location.dto.LocationDto;
import ru.practicum.mainservice.location.dto.LocationFullDto;
import ru.practicum.mainservice.location.dto.NewLocationDto;
import ru.practicum.mainservice.user.User;
import ru.practicum.mainservice.user.UserMapper;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LocationEntityMapper {
    public static LocationEntity newLocationDtoToLocation(NewLocationDto newLocationDto, LocationStatus status,
                                                          User owner) {
        return LocationEntity.builder()
                .name(newLocationDto.getName())
                .lat(newLocationDto.getLocation().getLat())
                .lon(newLocationDto.getLocation().getLon())
                .radius(newLocationDto.getRadius())
                .status(status)
                .owner(owner)
                .build();
    }

    public static LocationDto locationToLocationDto(LocationEntity location) {
        LocationDto locationDto = new LocationDto();
        locationDto.setId(location.getId());
        locationDto.setLocation(LocationMapper.coordsToLocation(location.getLat(), location.getLon()));
        locationDto.setRadius(location.getRadius());
        locationDto.setName(location.getName());
        locationDto.setStatus(location.getStatus());
        return locationDto;
    }

    public static LocationFullDto locationToLocationFullDto(LocationEntity location) {
        LocationFullDto locationDto = new LocationFullDto();
        locationDto.setId(location.getId());
        locationDto.setLocation(LocationMapper.coordsToLocation(location.getLat(), location.getLon()));
        locationDto.setRadius(location.getRadius());
        locationDto.setName(location.getName());
        locationDto.setStatus(location.getStatus());
        User user = location.getOwner();
        if (user != null) {
            locationDto.setOwner(UserMapper.userToUserShortDto(location.getOwner()));
        }
        return locationDto;
    }


    public static List<LocationDto> locationToLocationDto(List<LocationEntity> locations) {
        return locations.stream()
                .map(LocationEntityMapper::locationToLocationDto)
                .collect(Collectors.toList());
    }

    public static List<LocationFullDto> locationToLocationFullDto(List<LocationEntity> locations) {
        return locations.stream()
                .map(LocationEntityMapper::locationToLocationFullDto)
                .collect(Collectors.toList());
    }
}
