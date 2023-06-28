package ru.practicum.mainservice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.statsservice.client.StatsClient;
import ru.practicum.statsservice.library.EndpointHitDto;

import java.time.LocalDateTime;

@RestController
public class ExampleController {

    private final StatsClient statsClient;

    public ExampleController(StatsClient statsClient) {
        this.statsClient = statsClient;
    }

    @GetMapping("/hello")
    public void hello() {
        EndpointHitDto hitDto = new EndpointHitDto();
        hitDto.setApp("app");
        hitDto.setIp("127.0.0.1");
        hitDto.setTimestamp(LocalDateTime.now());
        hitDto.setUri("/events");
        ResponseEntity<EndpointHitDto> hit;
        try {
            hit = statsClient.hit(hitDto);
        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }
}
