package ru.practicum.mainservice.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.client.StatsClient;
import ru.practicum.mainservice.event.dto.ViewStats;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class StatsService {

    private static final long PLUS_INTERVAL = 5;
    private static final long MINUS_INTERVAL = 5;

    private final StatsClient client;

    public List<ViewStats> getStatsSearch(LocalDateTime start, boolean unique, List<String> uris, int page, int size) {
        // TODO: 07.07.2023 change year to hours
        LocalDateTime endS = LocalDateTime.now().plusYears(MINUS_INTERVAL);

        Object statsList = client.stats(start.minusYears(PLUS_INTERVAL), endS, unique, uris, page, size).getBody();
        ObjectMapper mapper = new ObjectMapper();
        List<ViewStats> stats = mapper.convertValue(statsList, new TypeReference<List<ViewStats>>() {
        });
        return stats;
    }
}
