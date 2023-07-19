package ru.practicum.mainservice.location.dto;

import lombok.Data;
import ru.practicum.mainservice.event.dto.Location;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Новое локация
 */
@Data
public class NewLocationDto {

    @NotNull
    private Location location;

    @Min(1)
    private Long radius; // радиус в метрах

    @NotBlank
    @Size(min = 3, max = 250)
    private String name;
}
