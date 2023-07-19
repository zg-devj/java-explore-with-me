package ru.practicum.mainservice.location.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.location.LocationService;
import ru.practicum.mainservice.location.dto.LocationDto;
import ru.practicum.mainservice.location.dto.NewLocationDto;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class PrivateLocationController {

    private final LocationService locationService;

    // Добавление локации пользователем со статусом ожидания
    @PostMapping("/{userId}/locations")
    @ResponseStatus(HttpStatus.CREATED)
    public LocationDto initiatorAddLocation(
            @PathVariable long userId,
            @RequestBody @Valid NewLocationDto newLocationDto) {
        return locationService.initiatorAddLocation(userId,newLocationDto);
    }
}
