package ru.practicum.mainservice.location.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.mainservice.event.dto.Location;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LocationShortDto {
    private Long id;

    private Location location;

    private Long radius; // радиус в метрах

    private String name;
}
