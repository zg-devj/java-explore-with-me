package ru.practicum.mainservice.request;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.mainservice.user.User;

import java.util.List;
import java.util.Optional;

public interface EventRequestRepository extends JpaRepository<EventRequest, Long> {

    // получение всех заявок пользователя на участие в событиях
    List<EventRequest> findAllByRequester(User requestor);

    // получение заявки по id заявки и пользователю
    Optional<EventRequest> findFirstByIdAndRequesterId(long requestId, long requestorId);

    // получение заявок по событию
    List<EventRequest> findAllByEventId(long eventId);

    List<EventRequest> findAllByEventIdAndStatusNot(long eventId, RequestStatus status);

    @Query("update EventRequest as er set er.status=?2 where er.event.id=?1")
    void updateAllByEventIdAndStatus(long eventId, RequestStatus status);
}
