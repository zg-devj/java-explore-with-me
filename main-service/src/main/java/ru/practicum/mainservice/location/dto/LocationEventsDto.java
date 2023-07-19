package ru.practicum.mainservice.location.dto;

import lombok.*;
import ru.practicum.mainservice.event.dto.EventShortDto;

import java.util.List;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LocationEventsDto extends LocationShortDto {
    List<EventShortDto> events;
}
