package ru.practicum.statsservice.server.stats;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.statsservice.library.EndpointHitDto;
import ru.practicum.statsservice.library.ViewStatsDto;

import javax.validation.Valid;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class StatsController {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final StatsService statsService;

    @PostMapping("/hit")
    public ResponseEntity<EndpointHitDto> hit(@RequestBody @Valid EndpointHitDto hitDto) {
        return new ResponseEntity<>(statsService.hit(hitDto), HttpStatus.CREATED);
    }

    @GetMapping("/stats")
    public List<ViewStatsDto> stats(
            @RequestParam String start,
            @RequestParam String end,
            @RequestParam(required = false) List<String> uris,
            @RequestParam(required = false, defaultValue = "false") boolean unique,
            Pageable pageable
    ) {
        LocalDateTime startD = decodedDateTime(start);
        LocalDateTime endD = decodedDateTime(end);

        return statsService.stats(startD, endD, uris, unique, pageable);
    }

    private LocalDateTime decodedDateTime(String datetime) {
        return LocalDateTime.parse(URLDecoder.decode(datetime, StandardCharsets.UTF_8), DATE_TIME_FORMATTER);
    }
}
