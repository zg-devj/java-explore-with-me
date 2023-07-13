package ru.practicum.mainservice.event.criteria;

import org.springframework.data.jpa.domain.Specification;
import ru.practicum.mainservice.event.Event;
import ru.practicum.mainservice.event.EventState;
import ru.practicum.mainservice.event.Event_;
import ru.practicum.mainservice.request.EventRequest;
import ru.practicum.mainservice.request.EventRequest_;
import ru.practicum.mainservice.user.User_;

import javax.persistence.criteria.Join;
import java.time.LocalDateTime;
import java.util.List;

public class EventSpecs {
    public static Specification<Event> isUsersIn(List<Long> users) {
        return (root, query, criteriaBuilder) -> {
            if (users != null && !users.isEmpty()) {
                return root.get(Event_.USER).in(users);
            } else {
                return criteriaBuilder.and();
            }
        };
    }

    public static Specification<Event> isCategoriesIn(List<Long> categories) {
        return (root, query, criteriaBuilder) -> {
            if (categories != null && !categories.isEmpty()) {
                return root.get(Event_.CATEGORY).in(categories);
            } else {
                return criteriaBuilder.and();
            }
        };
    }

    public static Specification<Event> isStatesIn(List<EventState> states) {
        return (root, query, criteriaBuilder) -> {
            if (states != null && !states.isEmpty()) {
                return root.get(Event_.STATE).in(states);
            } else {
                return criteriaBuilder.and();
            }
        };
    }
}
