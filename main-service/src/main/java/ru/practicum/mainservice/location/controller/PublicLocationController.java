package ru.practicum.mainservice.location.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.location.LocationService;
import ru.practicum.mainservice.location.dto.LocationDto;
import ru.practicum.mainservice.location.dto.LocationEventsDto;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/locations")
public class PublicLocationController {

    private final LocationService locationService;

    // Все опубликованные локации
    @GetMapping
    public List<LocationDto> findAllLocations(
            @RequestParam(defaultValue = "0") @PositiveOrZero int from,
            @RequestParam(defaultValue = "10") @Positive int size
    ) {
        log.info("GET /locations?from{}&size={} - All published locations.", from, size);
        return locationService.findAllLocations(from, size);
    }


    // Поиск события в текущей локации
    @GetMapping("/{locationId}/events")
    public LocationEventsDto findEventsInLocation(
            @PathVariable Long locationId,
            @RequestParam(defaultValue = "0") @PositiveOrZero int from,
            @RequestParam(defaultValue = "10") @Positive int size
    ) {
        log.info("GET /locations/{}/events?from{}&size={} - Search for an event in the current location.", locationId,
                from, size);
        return locationService.findEventsInLocation(locationId, from, size);
    }
}
