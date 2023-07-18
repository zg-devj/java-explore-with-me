package ru.practicum.mainservice.location.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.event.dto.EventShortDto;
import ru.practicum.mainservice.location.dto.LocationDto;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/locations")
public class PublicLocationController {

    // Все опубликованные локации
    @GetMapping
    public List<LocationDto> findAllLocations() {
        return null;
    }


    // Поиск события в текущей локации
    @GetMapping("/{locationId}/events")
    public List<EventShortDto> findEventsInLocation(
            @PathVariable Long locationId
    ) {
        return null;
    }
}
