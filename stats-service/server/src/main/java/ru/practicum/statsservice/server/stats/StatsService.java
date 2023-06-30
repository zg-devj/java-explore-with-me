package ru.practicum.statsservice.server.stats;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.statsservice.common.EndpointHitDto;
import ru.practicum.statsservice.common.ViewStatsDto;

import java.time.LocalDateTime;
import java.util.List;

@Service
public interface StatsService {
    EndpointHitDto hit(EndpointHitDto hitDto);

    List<ViewStatsDto> stats(LocalDateTime start, LocalDateTime end,
                             List<String> uris, boolean unique, Pageable pageable);
}
