package ru.practicum.server.stats.mapper;

import org.mapstruct.Mapper;
import ru.practicum.common.EndpointHitDto;
import ru.practicum.common.ViewStatsDto;
import ru.practicum.server.stats.Stats;
import ru.practicum.server.stats.ViewStats;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StatsMapper {
    Stats endpointHitDtoToStats(EndpointHitDto hitDto);

    EndpointHitDto statsToEndpointHitDto(Stats stats);

    List<ViewStatsDto> viewStatsToViewStatsDto(List<ViewStats> viewStats);
}
