package ru.practicum.mainservice.category;

import lombok.*;

import javax.persistence.*;

/**
 * Категория
 */
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // название категории
    private String name;
}
