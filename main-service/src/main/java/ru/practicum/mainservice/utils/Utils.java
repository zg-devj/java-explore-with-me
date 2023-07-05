package ru.practicum.mainservice.utils;

import org.springframework.data.domain.PageRequest;

public class Utils {
    public static PageRequest getPageRequest(int from, int size) {
        int page = from / size;
        return PageRequest.of(page, size);
    }
}
