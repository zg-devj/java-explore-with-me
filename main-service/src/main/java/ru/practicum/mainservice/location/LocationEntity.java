package ru.practicum.mainservice.location;

import ru.practicum.mainservice.event.EventState;

import javax.persistence.*;

@Entity
@Table(name = "locations")
public class LocationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "location_lat")
    private Double lat;

    @Column(name = "location_lon")
    private Double lon;

    private String name;

    private long radius;

    @Enumerated(EnumType.STRING)
    private LocationStatus status;
}
