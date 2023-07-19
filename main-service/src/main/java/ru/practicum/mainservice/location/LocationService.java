package ru.practicum.mainservice.location;

import ru.practicum.mainservice.location.dto.LocationDto;
import ru.practicum.mainservice.location.dto.NewLocationDto;
import ru.practicum.mainservice.location.dto.UpdateLocationRequest;

import java.util.List;

public interface LocationService {

    // admin

    public List<LocationDto> adminFindLocations(List<LocationStatus> status, int from, int size);

    public LocationDto adminCreateLocation(NewLocationDto newLocationDto);

    public LocationDto adminUpdateLocation(long locationId, UpdateLocationRequest locationRequest);

    public void adminDeleteLocation(long locationId);

    // private

    public LocationDto initiatorAddLocation(long userId, NewLocationDto newLocationDto);
}
