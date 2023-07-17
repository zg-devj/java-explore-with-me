package ru.practicum.mainservice.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.format.DateTimeFormatter;

public class Util {
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static PageRequest getPageRequest(int from, int size) {
        int page = from / size;
        return PageRequest.of(page, size);
    }

    public static PageRequest getPageRequest(int from, int size, Sort sort) {
        int page = from / size;
        return PageRequest.of(page, size, sort);
    }
}
