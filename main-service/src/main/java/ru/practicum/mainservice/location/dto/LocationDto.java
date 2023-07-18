package ru.practicum.mainservice.location.dto;

import ru.practicum.mainservice.event.dto.Location;
import ru.practicum.mainservice.location.LocationStatus;

import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;

/**
 * Локация
 */
public class LocationDto {
    private Long id;
    private Location location;
    // радиус в метрах
    private Long radius;
    private String name;
    private LocationStatus status;
}
