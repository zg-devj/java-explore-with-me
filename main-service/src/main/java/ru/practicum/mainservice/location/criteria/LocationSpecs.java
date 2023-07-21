package ru.practicum.mainservice.location.criteria;

import org.springframework.data.jpa.domain.Specification;
import ru.practicum.mainservice.location.LocationEntity;
import ru.practicum.mainservice.location.LocationEntity_;
import ru.practicum.mainservice.location.LocationStatus;

import java.util.List;

public class LocationSpecs {

    // статусы локации
    public static Specification<LocationEntity> isLocationStatusIn(List<LocationStatus> statuses) {
        return (root, query, criteriaBuilder) -> {
            if (statuses != null && !statuses.isEmpty()) {
                return root.get(LocationEntity_.STATUS).in(statuses);
            } else {
                return criteriaBuilder.and();
            }
        };
    }
}
