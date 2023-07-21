package ru.practicum.mainservice.location.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.location.LocationService;
import ru.practicum.mainservice.location.dto.LocationDto;
import ru.practicum.mainservice.location.dto.NewLocationDto;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class PrivateLocationController {

    private final LocationService locationService;

    // Добавление локации пользователем
    // Со статусом PENDING
    @PostMapping("/{userId}/locations")
    @ResponseStatus(HttpStatus.CREATED)
    public LocationDto initiatorAddLocation(
            @PathVariable long userId,
            @RequestBody @Valid NewLocationDto newLocationDto) {
        log.info("POST /users/{}/locations - Adding a location by the user.", userId);
        return locationService.initiatorAddLocation(userId, newLocationDto);
    }
}
