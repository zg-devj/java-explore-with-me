package ru.practicum.server.stats;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.common.EndpointHitDto;
import ru.practicum.common.ViewStatsDto;
import ru.practicum.server.stats.exceptions.BadRequestException;

import javax.validation.Valid;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class StatsController {

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final StatsService statsService;

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public EndpointHitDto hit(@RequestBody @Valid EndpointHitDto hitDto) {
        return statsService.hit(hitDto);
    }

    @GetMapping("/stats")
    public List<ViewStatsDto> stats(
            @RequestParam String start,
            @RequestParam String end,
            @RequestParam(required = false) List<String> uris,
            @RequestParam(required = false, defaultValue = "false") boolean unique
    ) {
        LocalDateTime startD = decodedDateTime(start);
        LocalDateTime endD = decodedDateTime(end);

        if (startD.isAfter(endD)) {
            throw new BadRequestException("The start date must not be later than the end date");
        }

        return statsService.stats(startD, endD, uris, unique);
    }

    private LocalDateTime decodedDateTime(String datetime) {
        return LocalDateTime.parse(URLDecoder.decode(datetime, StandardCharsets.UTF_8), DATE_TIME_FORMATTER);
    }
}
