package ru.practicum.mainservice.event;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import ru.practicum.mainservice.category.Category;
import ru.practicum.mainservice.request.EventRequest;
import ru.practicum.mainservice.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Builder
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
    @Column(name = "created_on")
    private LocalDateTime createdOn;

    // Дата и время на которые намечено событие. Дата и время указываются в формате "yyyy-MM-dd HH:mm:ss"
    @Column(name = "event_date")
    private LocalDateTime eventDate;

    // Дата и время публикации события (в формате "yyyy-MM-dd HH:mm:ss")
    @Column(name = "published_on")
    private LocalDateTime publishedOn;

    // Нужно ли оплачивать участие в событии
    private Boolean paid;

    // Ограничение на количество участников. Значение 0 - означает отсутствие ограничения
    @Column(name = "participant_limit")
    private Integer participantLimit;

    // Количество одобренных заявок на участие в данном событии
    @Column(name = "confirmed_requests")
    private Long confirmedRequests;

    // Нужна ли пре-модерация заявок на участие.
    // Если true (default), то все заявки будут ожидать подтверждения инициатором события.
    // Если false - то будут подтверждаться автоматически.
    @Column(name = "request_moderation")
    private Boolean requestModeration;

    // Список состояний жизненного цикла события
    @Enumerated(EnumType.STRING)
    private EventState state;

    // место проведения события широта
    @Column(name = "location_lat")
    private Double lat;

    // место проведения события долгота
    @Column(name = "location_lon")
    private Double lon;

    // категория
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    // владелец события
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

//    @OneToMany(mappedBy = "event", fetch = FetchType.LAZY)
//    private List<EventRequest> requests;

//    @ManyToMany(mappedBy = "events")
//    private Set<Compilation> compilations;
}
