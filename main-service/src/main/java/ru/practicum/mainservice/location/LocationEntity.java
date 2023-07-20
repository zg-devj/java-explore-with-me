package ru.practicum.mainservice.location;

import lombok.*;
import ru.practicum.mainservice.user.User;

import javax.persistence.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "locations")
public class LocationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "location_lat")
    private Double lat;

    @Column(name = "location_lon")
    private Double lon;

    private String name;

    private long radius;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;

    @Enumerated(EnumType.STRING)
    private LocationStatus status;
}
