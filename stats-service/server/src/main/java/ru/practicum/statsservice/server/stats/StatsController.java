package ru.practicum.statsservice.server.stats;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.statsservice.library.EndpointHitDto;

import javax.validation.Valid;

@RestController
public class StatsController {

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public void hit(@RequestBody @Valid EndpointHitDto hitDto) {
        System.out.println(hitDto.toString());
    }
}
