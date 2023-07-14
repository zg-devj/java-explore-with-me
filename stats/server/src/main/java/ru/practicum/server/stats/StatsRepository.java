package ru.practicum.server.stats;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsRepository extends JpaRepository<Stats, Long> {

    // without uri
    @Query("select new ru.practicum.server.stats.ViewStats(s.app,s.uri,count(s.uri)) " +
            "from Stats as s " +
            "where s.timestamp between :start and :end " +
            "group by s.app, s.uri " +
            "order by count(s.uri) desc")
    List<ViewStats> findRes(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    // without uri, unique ip
    @Query("select new ru.practicum.server.stats.ViewStats(s.app,s.uri,count(distinct(s.uri))) " +
            "from Stats as s " +
            "where s.timestamp between :start and :end " +
            "group by s.app, s.uri, s.ip " +
            "order by count(s.uri) desc")
    List<ViewStats> findResUniqueIP(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    // with uri
    @Query("select new ru.practicum.server.stats.ViewStats(s.app,s.uri,count(s.uri)) " +
            "from Stats as s " +
            "where s.uri in :uris and s.timestamp between :start and :end " +
            "group by s.app, s.uri " +
            "order by count(s.uri) desc")
    List<ViewStats> findResInUri(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end,
                                    @Param("uris") List<String> uris);

    // with uri, unique ip
    @Query("select new ru.practicum.server.stats.ViewStats(s.app,s.uri,count(distinct(s.uri))) " +
            "from Stats as s " +
            "where s.uri in :uris and s.timestamp between :start and :end " +
            "group by s.app, s.uri, s.ip " +
            "order by count(s.uri) desc")
    List<ViewStats> findResUniqueIPInUri(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end,
                                            @Param("uris") List<String> uris);
}
