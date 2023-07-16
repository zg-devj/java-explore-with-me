package ru.practicum.mainservice.compilation;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.common.ViewStatsDto;
import ru.practicum.mainservice.compilation.dto.CompilationDto;
import ru.practicum.mainservice.event.EventMapper;
import ru.practicum.mainservice.event.dto.EventShortDto;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CompilationMapper {


    public static CompilationDto compilationToCompilationDto(Compilation compilation, List<EventShortDto> events) {
        return CompilationDto.builder()
                .id(compilation.getId())
                .title(compilation.getTitle())
                .events(events)
                .pinned(compilation.getPinned())
                .build();
    }
}
