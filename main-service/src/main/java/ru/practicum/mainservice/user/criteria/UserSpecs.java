package ru.practicum.mainservice.user.criteria;

import org.springframework.data.jpa.domain.Specification;
import ru.practicum.mainservice.user.User;
import ru.practicum.mainservice.user.User_;

import java.util.List;

public class UserSpecs {
    public static Specification<User> isIdsIn(List<Long> ids) {
        return (root, query, criteriaBuilder) -> {
            if (ids != null && !ids.isEmpty()) {
                return root.get(User_.id).in(ids);
            } else {
                return criteriaBuilder.and();
            }
        };
    }
}
