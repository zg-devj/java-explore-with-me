package ru.practicum.mainservice.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.client.StatsClient;
import ru.practicum.common.EndpointHitDto;
import ru.practicum.common.ViewStatsDto;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class StatsService {

    private static final long INTERVAL_HOUR = 1;

    private final StatsClient client;

    public void hit(EndpointHitDto endpointHitDto) {
        client.hit(endpointHitDto);
    }

    public List<ViewStatsDto> getStatsSearchInterval(LocalDateTime start, boolean unique, List<String> uris) {

        LocalDateTime endS = LocalDateTime.now().plusHours(INTERVAL_HOUR);

        return getStatsSearchInterval(start, endS, unique, uris);
    }

    public List<ViewStatsDto> getStatsSearchInterval(LocalDateTime start, LocalDateTime end, boolean unique, List<String> uris) {

        Object statsList = client.stats(start.minusHours(INTERVAL_HOUR), end, unique, uris).getBody();
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(statsList, new TypeReference<List<ViewStatsDto>>() {
        });
    }

    public List<ViewStatsDto> getStatsSearch(LocalDateTime start, LocalDateTime end, boolean unique, List<String> uris) {

        Object statsList = client.stats(start, end, unique, uris).getBody();
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(statsList, new TypeReference<List<ViewStatsDto>>() {
        });
    }
}
