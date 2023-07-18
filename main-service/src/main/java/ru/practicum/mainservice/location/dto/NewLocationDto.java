package ru.practicum.mainservice.location.dto;

import lombok.Data;
import ru.practicum.mainservice.event.dto.Location;

import javax.validation.constraints.*;

/**
 * Новое локация
 */
@Data
public class NewLocationDto {

    @NotNull
    private Location location;
    @Positive
    @Min(1)
    private Long radius; // радиус в метрах
    @NotBlank
    @Size(min = 3, max = 100)
    private String name;
}
