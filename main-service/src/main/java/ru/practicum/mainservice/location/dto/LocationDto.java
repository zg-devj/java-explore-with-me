package ru.practicum.mainservice.location.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.mainservice.event.dto.Location;
import ru.practicum.mainservice.location.LocationStatus;

/**
 * Локация
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LocationDto {

    private Long id;

    private Location location;

    private Long radius; // радиус в метрах

    private String name;

    private LocationStatus status;
}
