package ru.practicum.server.stats;

import org.springframework.data.domain.Pageable;
import ru.practicum.common.EndpointHitDto;
import ru.practicum.common.ViewStatsDto;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsService {
    EndpointHitDto hit(EndpointHitDto hitDto);

    List<ViewStatsDto> stats(LocalDateTime start, LocalDateTime end,
                             List<String> uris, boolean unique, Pageable pageable);
}
