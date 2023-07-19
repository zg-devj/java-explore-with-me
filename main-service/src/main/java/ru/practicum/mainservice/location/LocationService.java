package ru.practicum.mainservice.location;

import ru.practicum.mainservice.location.dto.LocationDto;
import ru.practicum.mainservice.location.dto.LocationEventsDto;
import ru.practicum.mainservice.location.dto.NewLocationDto;
import ru.practicum.mainservice.location.dto.UpdateLocationRequest;

import java.util.List;

public interface LocationService {

    // admin

    List<LocationDto> adminFindLocations(List<LocationStatus> statuses, int from, int size);

    LocationDto adminCreateLocation(NewLocationDto newLocationDto);

    LocationDto adminUpdateLocation(long locationId, UpdateLocationRequest locationRequest);

    void adminDeleteLocation(long locationId);

    // private

    LocationDto initiatorAddLocation(long userId, NewLocationDto newLocationDto);

    // public

    List<LocationDto> findAllLocations(int from, int size);

    LocationEventsDto findEventsInLocation(long locationId, int from, int size);
}
