package ru.practicum.mainservice.location.dto;

import ru.practicum.mainservice.event.dto.Location;
import ru.practicum.mainservice.location.LocationStatusAction;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

/**
 * Обновление локации
 */
public class UpdateLocationRequest {
    private Location location;
    @Min(1)
    private Long radius;
    @Size(min = 3, max = 100)
    private String name;
    private LocationStatusAction action;
}
