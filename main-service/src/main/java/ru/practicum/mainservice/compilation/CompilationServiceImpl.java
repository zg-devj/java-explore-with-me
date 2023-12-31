package ru.practicum.mainservice.compilation;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.common.ViewStatsDto;
import ru.practicum.mainservice.compilation.dto.CompilationDto;
import ru.practicum.mainservice.compilation.dto.NewCompilationDto;
import ru.practicum.mainservice.compilation.dto.UpdateCompilationRequest;
import ru.practicum.mainservice.event.Event;
import ru.practicum.mainservice.event.EventMapper;
import ru.practicum.mainservice.event.EventRepository;
import ru.practicum.mainservice.event.dto.EventShortDto;
import ru.practicum.mainservice.exceptions.NotFoundException;
import ru.practicum.mainservice.services.StatsService;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static ru.practicum.mainservice.compilation.criteria.CompilationSpecs.isPinnedTrue;
import static ru.practicum.mainservice.utils.Util.getPageRequest;

@Service
@RequiredArgsConstructor
public class CompilationServiceImpl implements CompilationService {

    private final StatsService statsService;

    private final EventRepository eventRepository;
    private final CompilationRepository compilationRepository;

    // private

    @Override
    @Transactional
    public CompilationDto adminAddCompilation(NewCompilationDto newCompilationDto) {
        Set<Event> events = new HashSet<>();
        if (newCompilationDto.getEvents() != null && !newCompilationDto.getEvents().isEmpty()) {
            events = eventRepository.findAllByIdIn(newCompilationDto.getEvents());
        }

        Compilation compilation = Compilation.builder()
                .pinned(newCompilationDto.isPinned())
                .title(newCompilationDto.getTitle())
                .events(events)
                .build();

        Compilation saved = compilationRepository.save(compilation);
        return getCompilationDto(saved);
    }

    @Override
    public void adminRemoveCompilation(long compilationId) {
        compilationRepository.deleteById(compilationId);
    }

    @Override
    public CompilationDto adminUpdateCompilation(long compilationId, UpdateCompilationRequest compilationRequest) {
        Compilation compilation = compilationRepository.findById(compilationId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Compilation with id=%d was not found", compilationId)));

        compilation.setPinned(compilationRequest.isPinned());

        if (compilationRequest.getTitle() != null) {
            compilation.setTitle(compilationRequest.getTitle());
        }

        if (compilationRequest.getEvents() != null && !compilationRequest.getEvents().isEmpty()) {
            Set<Event> events = eventRepository.findAllByIdIn(compilationRequest.getEvents());
            for (Event event : events) {
                if (!compilation.getEvents().contains(event)) {
                    compilation.getEvents().add(event);
                }
            }
        }

        Compilation saved = compilationRepository.save(compilation);
        return getCompilationDto(saved);
    }

    // public

    @Override
    public List<CompilationDto> getCompilations(boolean pinned, int from, int size) {

        PageRequest pageRequest = getPageRequest(from, size);

        List<Compilation> compilations = compilationRepository.findAll(
                isPinnedTrue(pinned), pageRequest).getContent();

        List<Long> eventIds = new ArrayList<>();
        for (Compilation compilation : compilations) {
            if (compilation.getEvents() != null && !compilation.getEvents().isEmpty()) {
                eventIds.addAll(compilation.getEvents().stream()
                        .map(Event::getId)
                        .collect(Collectors.toList()));
            }
        }

        LocalDateTime start = LocalDateTime.now().minusYears(1000);
        LocalDateTime end = LocalDateTime.now().plusHours(1);

        List<String> uris = eventIds.stream()
                .map(x -> "/events/" + x)
                .collect(Collectors.toList());

        List<ViewStatsDto> stats = statsService.getStatsSearchInterval(start, end, false, uris);

        List<CompilationDto> list = new ArrayList<>();

        for (Compilation compilation : compilations) {
            if (compilation.getEvents() != null && !compilation.getEvents().isEmpty()) {
                // если есть события
                List<EventShortDto> shortDtos = EventMapper.eventToEventShortDto(
                        new ArrayList<>(compilation.getEvents()), stats);
                list.add(CompilationMapper.compilationToCompilationDto(compilation, shortDtos));
            } else {
                // если нет событий
                list.add(CompilationMapper.compilationToCompilationDto(compilation, new ArrayList<>()));
            }
        }

        return list;
    }

    @Override
    public CompilationDto getCompilation(long compilationId) {
        Compilation compilation = compilationRepository.findById(compilationId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Compilation with id=%d was not found", compilationId)));

        return getCompilationDto(compilation);
    }

    private CompilationDto getCompilationDto(Compilation compilation) {
        if (compilation.getEvents() != null && !compilation.getEvents().isEmpty()) {

            List<Long> eventIds = compilation.getEvents().stream()
                    .map(Event::getId)
                    .collect(Collectors.toList());

            LocalDateTime start = compilation.getEvents().stream().min(Comparator.comparing(Event::getCreatedOn))
                    .get().getCreatedOn();

            LocalDateTime end = LocalDateTime.now().plusHours(1);

            List<String> uris = eventIds.stream()
                    .map(x -> "/events/" + x)
                    .collect(Collectors.toList());

            List<ViewStatsDto> stats = statsService.getStatsSearchInterval(start, end, false, uris);

            List<EventShortDto> eventShortDtos = EventMapper.eventToEventShortDto(
                    new ArrayList<>(compilation.getEvents()), stats);
            return CompilationMapper.compilationToCompilationDto(compilation, eventShortDtos);
        } else {
            return CompilationMapper.compilationToCompilationDto(compilation, new ArrayList<>());
        }
    }
}
