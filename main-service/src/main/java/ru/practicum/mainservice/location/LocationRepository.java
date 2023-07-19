package ru.practicum.mainservice.location;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<LocationEntity, Long> {

    // удаление локации по id и статусу
    void deleteByIdAndStatus(long id, LocationStatus status);
}
