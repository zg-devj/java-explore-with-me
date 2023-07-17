package ru.practicum.mainservice.compilation.criteria;

import org.springframework.data.jpa.domain.Specification;
import ru.practicum.mainservice.compilation.Compilation;
import ru.practicum.mainservice.compilation.Compilation_;

public class CompilationSpecs {

    // только прикрепленные подборки к главной страницы.
    public static Specification<Compilation> isPinnedTrue(Boolean pinned) {
        return (root, query, criteriaBuilder) -> {
            if (pinned != null && pinned) {
                return criteriaBuilder.equal(root.get(Compilation_.PINNED), true);
            } else {
                return criteriaBuilder.and();
            }
        };
    }
}
