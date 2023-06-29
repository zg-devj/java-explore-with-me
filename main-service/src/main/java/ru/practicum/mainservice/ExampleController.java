package ru.practicum.mainservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.statsservice.client.StatsClient;
import ru.practicum.statsservice.library.EndpointHitDto;
import ru.practicum.statsservice.library.ViewStatsDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ExampleController {

    private final StatsClient statsClient;

    public ExampleController(StatsClient statsClient) {
        this.statsClient = statsClient;
    }

    @GetMapping("/hello")
    public ResponseEntity<Object> hello() {
        EndpointHitDto hitDto = new EndpointHitDto();
        hitDto.setApp("app");
        hitDto.setIp("127.0.0.1");
        hitDto.setTimestamp(LocalDateTime.now());
        hitDto.setUri("/events");
        try {
            ResponseEntity<Object> hit = statsClient.hit(hitDto);
            return hit;
        } catch (Exception e) {
            System.out.println(e.toString());
            throw new RuntimeException(e.toString());
        }
    }

    @GetMapping("/info")
    public ResponseEntity<Object> info() {
        LocalDateTime start = LocalDateTime.now().minusYears(2);
        LocalDateTime end = LocalDateTime.now().plusYears(2);
        boolean unique = false;
        List<String> uris = new ArrayList<>();
        ResponseEntity<Object> stats = statsClient.stats(start, end, unique, uris);
        System.out.println(stats);
        return stats;
    }
}
