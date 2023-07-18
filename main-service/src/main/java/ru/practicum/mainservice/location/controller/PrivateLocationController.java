package ru.practicum.mainservice.location.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.location.dto.LocationDto;
import ru.practicum.mainservice.location.dto.NewLocationDto;

import javax.validation.Valid;

@RestController
@RequestMapping("/users/location")
public class PrivateLocationController {

    // Добавление локации пользователем со статусом ожидания
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LocationDto initiatorAddLocation(
            @RequestBody @Valid NewLocationDto newLocationDto) {
        return null;
    }
}
