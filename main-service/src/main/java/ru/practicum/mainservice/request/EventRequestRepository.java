package ru.practicum.mainservice.request;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.mainservice.event.Event;
import ru.practicum.mainservice.request.dto.ParticipationRequestDto;
import ru.practicum.mainservice.request.model.IConfirmedRequests;
import ru.practicum.mainservice.user.User;

import java.util.List;
import java.util.Optional;

public interface EventRequestRepository extends JpaRepository<EventRequest, Long> {

//    // получит количество подтвержденных запросов на события
//    @Query("select er.event.id as eventId, count(er.event.id) as confirmedCount from " +
//            "EventRequest as er where er.status='CONFIRMED' and er.event.id in (?1) " +
//            "group by er.event.id")
//    List<IConfirmedRequests> findConfirmedRequestCounts(List<Long> eventIds);
//
//    // получит количество подтвержденных запросов на событие
//    @Query("select er.event.id as eventId, count(er.event.id) as confirmedCount from " +
//            "EventRequest as er where er.status='CONFIRMED' and er.event.id=?1 " +
//            "group by er.event.id")
//    IConfirmedRequests findConfirmedRequestCount(long eventId);

    // получение всех заявок пользователя на участие в событиях
    List<EventRequest> findAllByRequester(User requestor);

    // получение заявки по id заявки и пользователю
    Optional<EventRequest> findFirstByIdAndRequesterId(long requestId, long requestorId);

    // получение заявок по событию
    List<EventRequest> findAllByEventId(long eventId);

    // получение запросов по идентификаторам, и событию
    List<EventRequest> findAllByIdInAndEvent(List<Long> ids, Event event);

    List<EventRequest> findAllByEventIdAndStatusNot(long eventId, RequestStatus status);

    @Query("update EventRequest as er set er.status=?2 where er.event.id=?1")
    void updateAllByEventIdAndStatus(long eventId, RequestStatus status);
}
