package ru.practicum.mainservice.location.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.mainservice.event.dto.Location;
import ru.practicum.mainservice.location.LocationStatusAction;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

/**
 * Обновление локации
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateLocationRequest {
    private Location location;

    @Min(1)
    private Long radius;

    @Size(min = 3, max = 250)
    private String name;

    private LocationStatusAction action;
}
