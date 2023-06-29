package ru.practicum.statsservice.server.stats;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.statsservice.library.ViewStatsDto;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StatsRepository extends JpaRepository<Stats, Long> {

    // without uri
    @Query("select new ru.practicum.statsservice.library.ViewStatsDto(s.app,s.uri,count(s.uri)) " +
            "from Stats as s " +
            "where s.timestamp between :start and :end " +
            "group by s.app, s.uri " +
            "order by count(s.uri) desc")
    Page<ViewStatsDto> findRes(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end,
                               Pageable pageable);

    // without uri, unique ip
    @Query("select new ru.practicum.statsservice.library.ViewStatsDto(s.app,s.uri,count(distinct(s.uri))) " +
            "from Stats as s " +
            "where s.timestamp between :start and :end " +
            "group by s.app, s.uri, s.ip " +
            "order by count(s.uri) desc")
    Page<ViewStatsDto> findResUniqueIP(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end,
                                       Pageable pageable);

    // with uri
    @Query("select new ru.practicum.statsservice.library.ViewStatsDto(s.app,s.uri,count(s.uri)) " +
            "from Stats as s " +
            "where s.uri in :uris and s.timestamp between :start and :end " +
            "group by s.app, s.uri " +
            "order by count(s.uri) desc")
    Page<ViewStatsDto> findResInUri(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end,
                                    @Param("uris") List<String> uris, Pageable pageable);

    // with uri, unique ip
    @Query("select new ru.practicum.statsservice.library.ViewStatsDto(s.app,s.uri,count(distinct(s.uri))) " +
            "from Stats as s " +
            "where s.uri in :uris and s.timestamp between :start and :end " +
            "group by s.app, s.uri, s.ip " +
            "order by count(s.uri) desc")
    Page<ViewStatsDto> findResUniqueIPInUri(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end,
                                            @Param("uris") List<String> uris, Pageable pageable);
}
