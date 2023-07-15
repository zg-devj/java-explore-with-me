package ru.practicum.mainservice.compilation;

import lombok.*;
import ru.practicum.mainservice.event.Event;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "compilation_events",
            joinColumns = { @JoinColumn(name = "compilation_id") },
            inverseJoinColumns = { @JoinColumn(name = "event_id") })
    private Set<Event> events = new HashSet<>();

    public void addEvent(Event event) {
        this.events.add(event);
        event.getCompilations().add(this);
    }

    public void removeEvent(long eventId) {
        Event event = this.events.stream().filter(e -> e.getId() == eventId)
                .findFirst().orElse(null);
        if (event != null) {
            this.events.remove(event);
            event.getCompilations().remove(this);
        }
    }
}
