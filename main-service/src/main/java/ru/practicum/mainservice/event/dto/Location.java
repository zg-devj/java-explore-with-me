package ru.practicum.mainservice.event.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * Широта и долгота места проведения события
 */
@Builder
@Getter
@Setter
public class Location {
    /**
     * Широта
     */
    private double lat;

    /**
     * Долгота
     */
    private double lon;
}
