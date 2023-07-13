package ru.practicum.mainservice.user;

import lombok.*;
import ru.practicum.mainservice.event.Event;

import javax.persistence.*;
import java.util.List;

/**
 * Пользователь
 */
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // имя пользователя
    private String name;

    // электронный адрес пользователя
    private String email;
}
