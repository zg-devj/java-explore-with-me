package ru.practicum.mainservice.compilation;

import lombok.*;
import ru.practicum.mainservice.event.Event;

import javax.persistence.*;
import java.util.List;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "compilations")
public class Compilation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Заголовок подборки
    private String title;

    // Закреплена ли подборка на главной странице сайта
    private Boolean pinned;

    @ManyToMany
    @JoinTable(
            name = "compilation_event",
            joinColumns = {@JoinColumn(name = "event_id")},
            inverseJoinColumns = {@JoinColumn(name = "compilation_id")}
    )
    private List<Event> events;
}
