package ru.practicum.statsservice.server.stats.mapper;

import org.mapstruct.Mapper;
import ru.practicum.statsservice.library.EndpointHitDto;
import ru.practicum.statsservice.server.stats.Stats;

@Mapper(componentModel = "spring")
public interface StatsMapper {
    Stats endpointHitDtoToStats(EndpointHitDto hitDto);

    EndpointHitDto statsToEndpointHitDto(Stats stats);
}
