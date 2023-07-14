package ru.practicum.mainservice.compilation;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.mainservice.compilation.dto.CompilationDto;
import ru.practicum.mainservice.compilation.dto.NewCompilationDto;
import ru.practicum.mainservice.compilation.dto.UpdateCompilationRequest;
import ru.practicum.mainservice.event.Event;
import ru.practicum.mainservice.event.EventMapper;
import ru.practicum.mainservice.event.EventRepository;
import ru.practicum.mainservice.event.dto.EventShortDto;
import ru.practicum.mainservice.event.dto.ViewStats;
import ru.practicum.mainservice.exceptions.NotFoundException;
import ru.practicum.mainservice.services.StatsService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
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
//    @Transactional
    public CompilationDto adminAddCompilation(NewCompilationDto newCompilationDto) {

        List<Event> events = new ArrayList<>();
        if (newCompilationDto.getEvents() != null && !newCompilationDto.getEvents().isEmpty()) {
            events = eventRepository.findAllByIdIn(newCompilationDto.getEvents());
        }

        //Compilation compilation = CompilationMapper.newCompilationDtoToCompilation(newCompilationDto, events);

        Compilation compilation = Compilation.builder()
                .pinned(newCompilationDto.isPinned())
                .title(newCompilationDto.getTitle())
                .build();

        Compilation saved = compilationRepository.save(compilation);
        saved.setEvents(events);
        Compilation saved2 = compilationRepository.save(saved);
        return getCompilationDto(saved2);
    }

    @Override
    public void adminRemoveCompilation(long compilationId) {
        compilationRepository.deleteById(compilationId);
    }

    @Override
    public CompilationDto adminUpdateCompilation(long compilationId, UpdateCompilationRequest request) {
        return null;
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

        List<ViewStats> stats = statsService.getStatsSearchInterval(start, end, false, uris);

        return CompilationMapper.compilationToCompilationDto(compilations, stats);
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

            List<ViewStats> stats = statsService.getStatsSearchInterval(start, end, false, uris);

            List<EventShortDto> eventShortDtos = EventMapper.eventToEventShortDto(compilation.getEvents(), stats);
            return CompilationMapper.compilationToCompilationDto(compilation, eventShortDtos);
        } else {
            return CompilationMapper.compilationToCompilationDto(compilation, new ArrayList<>());
        }
    }
}
