package ru.practicum.mainservice.event.criteria;

import org.springframework.data.jpa.domain.Specification;
import ru.practicum.mainservice.event.Event;
import ru.practicum.mainservice.event.EventState;
import ru.practicum.mainservice.event.Event_;

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

    public static Specification<Event> isPaid(Boolean paid) {
        return (root, query, criteriaBuilder) -> {
            if (paid != null) {
                return criteriaBuilder.equal(root.get(Event_.PAID), paid);
            } else {
                return criteriaBuilder.and();
            }
        };
    }

    public static Specification<Event> isTextAnnotation(String text) {
        return (root, query, criteriaBuilder) -> {
            if (text != null) {
                return criteriaBuilder.like(root.get(Event_.ANNOTATION), text);
            } else {
                return criteriaBuilder.and();
            }
        };
    }

    public static Specification<Event> isTextDescription(String text) {
        return (root, query, criteriaBuilder) -> {
            if (text != null) {
                return criteriaBuilder.like(root.get(Event_.DESCRIPTION), text);
            } else {
                return criteriaBuilder.and();
            }
        };
    }

    public static Specification<Event> isAvailable(boolean onlyAvailable) {
        return (root, query, criteriaBuilder) -> {
            if (onlyAvailable) {
                return criteriaBuilder.lessThan(root.get(Event_.PARTICIPANT_LIMIT), root.get(Event_.CONFIRMED_REQUESTS));
            } else {
                return criteriaBuilder.and();
            }
        };
    }

    public static Specification<Event> greaterDate(LocalDateTime start) {
        return (root, query, criteriaBuilder) -> {
            if (start != null) {
                return criteriaBuilder.greaterThan(root.get(Event_.EVENT_DATE), start);
            } else {
                return criteriaBuilder.and();
            }
        };
    }

    public static Specification<Event> lessDate(LocalDateTime end) {
        return (root, query, criteriaBuilder) -> {
            if (end != null) {
                return criteriaBuilder.lessThan(root.get(Event_.EVENT_DATE), end);
            } else {
                return criteriaBuilder.and();
            }
        };
    }
}
