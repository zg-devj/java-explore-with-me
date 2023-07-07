package ru.practicum.mainservice.request;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.mainservice.request.model.IConfirmedRequests;

import java.util.List;

public interface EventRequestRepository extends JpaRepository<EventRequest, Long> {

    @Query("select er.event.id as eventId, count(er.event.id) as confirmedCount from " +
            "EventRequest as er where er.status='CONFIRMED' and er.event.id in (?1) " +
            "group by er.event.id")
    List<IConfirmedRequests> findConfirmedRequestCounts(List<Long> eventIds);

    @Query("select er.event.id as eventId, count(er.event.id) as confirmedCount from " +
            "EventRequest as er where er.status='CONFIRMED' and er.event.id=?1 " +
            "group by er.event.id")
    IConfirmedRequests findConfirmedRequestCount(long eventId);
}
