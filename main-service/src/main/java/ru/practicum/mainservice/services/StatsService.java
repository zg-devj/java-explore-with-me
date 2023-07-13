package ru.practicum.mainservice.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.client.StatsClient;
import ru.practicum.common.EndpointHitDto;
import ru.practicum.mainservice.event.dto.ViewStats;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class StatsService {

    private static final long PLUS_INTERVAL = 5;
    private static final long MINUS_INTERVAL = 5;

    private final StatsClient client;

    public void hit(EndpointHitDto endpointHitDto) {
        client.hit(endpointHitDto);
    }

    public List<ViewStats> getStatsSearchInterval(LocalDateTime start, boolean unique, List<String> uris) {
        // TODO: 07.07.2023 change year to hours
        LocalDateTime endS = LocalDateTime.now().plusYears(MINUS_INTERVAL);

        return getStatsSearchInterval(start, endS, unique, uris);
    }

    public List<ViewStats> getStatsSearchInterval(LocalDateTime start, LocalDateTime end, boolean unique, List<String> uris) {

        Object statsList = client.stats(start.minusYears(PLUS_INTERVAL), end, unique, uris).getBody();
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(statsList, new TypeReference<List<ViewStats>>() {
        });
    }

    public List<ViewStats> getStatsSearch(LocalDateTime start, LocalDateTime end, boolean unique, List<String> uris) {

        Object statsList = client.stats(start, end, unique, uris).getBody();
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(statsList, new TypeReference<List<ViewStats>>() {
        });
    }
}
