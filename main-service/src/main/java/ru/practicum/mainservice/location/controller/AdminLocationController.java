package ru.practicum.mainservice.location.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.location.LocationService;
import ru.practicum.mainservice.location.LocationStatus;
import ru.practicum.mainservice.location.dto.LocationDto;
import ru.practicum.mainservice.location.dto.NewLocationDto;
import ru.practicum.mainservice.location.dto.UpdateLocationRequest;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/locations")
public class AdminLocationController {

    private final LocationService locationService;

    // Поиск локаций
    @GetMapping
    public List<LocationDto> adminFindLocations(
            @RequestParam(required = false) List<LocationStatus> statuses,
            @RequestParam(defaultValue = "0") @PositiveOrZero int from,
            @RequestParam(defaultValue = "10") @Positive int size
    ) {
        return locationService.adminFindLocations(statuses, from, size);
    }

    // Добавление локации
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LocationDto adminCreateLocation(
            @RequestBody @Valid NewLocationDto newLocationDto) {
        return locationService.adminCreateLocation(newLocationDto);
    }

    // Редактирование данных о локации
    @PatchMapping("/{locationId}")
    public LocationDto adminUpdateLocation(
            @PathVariable long locationId,
            @RequestBody @Valid UpdateLocationRequest updateLocationRequest
    ) {
        return locationService.adminUpdateLocation(locationId, updateLocationRequest);
    }

    // Удаление локации если она отклонена
    @DeleteMapping("/{locationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void adminDeleteLocation(
            @PathVariable long locationId) {
        locationService.adminDeleteLocation(locationId);
    }
}
