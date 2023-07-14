package ru.practicum.server.stats;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.common.EndpointHitDto;
import ru.practicum.common.ViewStatsDto;
import ru.practicum.server.stats.mapper.StatsMapper;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StatsServiceImpl implements StatsService {

    private final StatsRepository statsRepository;
    private final StatsMapper statsMapper;

    @Override
    @Transactional
    public EndpointHitDto hit(EndpointHitDto hitDto) {
        Stats stats = statsMapper.endpointHitDtoToStats(hitDto);
        return statsMapper.statsToEndpointHitDto(statsRepository.save(stats));
    }

    @Override
    public List<ViewStatsDto> stats(LocalDateTime start, LocalDateTime end,
                                    List<String> uris, boolean unique) {
        if (unique) {
            if (uris == null || uris.isEmpty()) {
                return statsMapper.viewStatsToViewStatsDto(statsRepository
                        .findResUniqueIP(start, end));
            } else {
                return statsMapper.viewStatsToViewStatsDto(statsRepository
                        .findResUniqueIPInUri(start, end, uris));
            }
        } else {
            if (uris == null || uris.isEmpty()) {
                return statsMapper.viewStatsToViewStatsDto(statsRepository
                        .findRes(start, end));
            } else {
                return statsMapper.viewStatsToViewStatsDto(statsRepository
                        .findResInUri(start, end, uris));
            }
        }

    }
}
