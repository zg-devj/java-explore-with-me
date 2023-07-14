package ru.practicum.mainservice.compilation;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.common.ViewStatsDto;
import ru.practicum.mainservice.compilation.dto.CompilationDto;
import ru.practicum.mainservice.compilation.dto.NewCompilationDto;
import ru.practicum.mainservice.event.Event;
import ru.practicum.mainservice.event.EventMapper;
import ru.practicum.mainservice.event.dto.EventShortDto;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CompilationMapper {
    public static Compilation newCompilationDtoToCompilation(NewCompilationDto dto, List<Event> events) {
        return Compilation.builder()
                .pinned(dto.isPinned())
                .events(events)
                .title(dto.getTitle())
                .build();
    }

    public static List<CompilationDto> compilationToCompilationDto(List<Compilation> compilations,
                                                                   List<ViewStatsDto> stats) {
        List<CompilationDto> list = new ArrayList<>();
        for (Compilation compilation : compilations) {
            if (compilation.getEvents() != null && !compilation.getEvents().isEmpty()) {
                // если есть события
                List<EventShortDto> shortDtos = EventMapper.eventToEventShortDto(compilation.getEvents(),stats);
                list.add(CompilationMapper.compilationToCompilationDto(compilation, shortDtos));
            } else {
                // если нет событий
                list.add(CompilationMapper.compilationToCompilationDto(compilation, new ArrayList<>()));
            }
        }
        return list;
    }

    public static CompilationDto compilationToCompilationDto(Compilation compilation, List<EventShortDto> events) {
        return CompilationDto.builder()
                .id(compilation.getId())
                .title(compilation.getTitle())
                .events(events)
                .pinned(compilation.getPinned())
                .build();
    }
}
