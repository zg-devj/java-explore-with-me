package ru.practicum.mainservice.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.mainservice.category.Category;
import ru.practicum.mainservice.compilation.Compilation;
import ru.practicum.mainservice.location.LocationEntity;
import ru.practicum.mainservice.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Заголовок события
    private String title;

    // краткое описание
    private String annotation;

    // Полное описание события
    private String description;

    // Дата и время создания события (в формате "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdOn;

    // Дата и время на которые намечено событие. Дата и время указываются в формате "yyyy-MM-dd HH:mm:ss"
    private LocalDateTime eventDate;

    // Дата и время публикации события (в формате "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime publishedOn;

    // Нужно ли оплачивать участие в событии
    private Boolean paid = false;

    // Ограничение на количество участников. Значение 0 - означает отсутствие ограничения
    private Integer participantLimit = 0;

    // Нужна ли пре-модерация заявок на участие.
    // Если true, то все заявки будут ожидать подтверждения инициатором события.
    // Если false - то будут подтверждаться автоматически.
    private Boolean requestModeration = true;

    // Список состояний жизненного цикла события
    @Enumerated(EnumType.STRING)
    private EventState state;

    // место проведения события
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", nullable = false)
    private LocationEntity location;

    // категория
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    // владелец события
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToMany(mappedBy = "events")
    private Set<Compilation> compilations;
}
