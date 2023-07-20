package ru.practicum.mainservice.event;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.mainservice.event.dto.Location;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LocationMapper {
    public static Location coordsToLocation(Double lat, Double lon) {
        return Location.builder()
                .lon(lon)
                .lat(lat)
                .build();
    }
}
