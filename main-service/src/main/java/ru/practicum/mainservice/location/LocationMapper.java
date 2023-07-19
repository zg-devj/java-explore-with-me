package ru.practicum.mainservice.location;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.mainservice.event.dto.Location;
import ru.practicum.mainservice.location.dto.LocationDto;
import ru.practicum.mainservice.location.dto.NewLocationDto;
import ru.practicum.mainservice.user.User;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LocationMapper {
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
        Location coordinate = Location.builder()
                .lon(location.getLon()).lat(location.getLat())
                .build();

        LocationDto locationDto = new LocationDto();
        locationDto.setId(location.getId());
        locationDto.setLocation(coordinate);
        locationDto.setRadius(location.getRadius());
        locationDto.setName(location.getName());
        locationDto.setStatus(location.getStatus());
        return locationDto;
    }

    public static List<LocationDto> locationToLocationDto(List<LocationEntity> locations) {
        return locations.stream()
                .map(LocationMapper::locationToLocationDto)
                .collect(Collectors.toList());
    }
}
