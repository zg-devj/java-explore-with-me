package ru.practicum.statsservice.server.stats;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.statsservice.common.EndpointHitDto;
import ru.practicum.statsservice.common.ViewStatsDto;
import ru.practicum.statsservice.server.stats.mapper.StatsMapper;

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
                                    List<String> uris, boolean unique, Pageable pageable) {
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
        if (unique) {
            if (uris == null || uris.isEmpty()) {
                return statsRepository.findResUniqueIP(start, end, pageRequest).getContent();
            } else {
                return statsRepository.findResUniqueIPInUri(start, end, uris, pageRequest).getContent();
            }
        } else {
            if (uris == null || uris.isEmpty()) {
                return statsRepository.findRes(start, end, pageRequest).getContent();
            } else {
                return statsRepository.findResInUri(start, end, uris, pageRequest).getContent();
            }
        }

    }
}
