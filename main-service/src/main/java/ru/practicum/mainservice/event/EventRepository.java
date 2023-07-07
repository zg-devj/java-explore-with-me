package ru.practicum.mainservice.event;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.mainservice.user.User;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {

    // получение списка событий инициатора
    List<Event> findEventsByUser(User user, Pageable pageable);

    // получение события по id и инициатору
    Optional<Event> findFirstByIdAndUser(Long id, User initiator);
}
