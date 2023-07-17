package ru.practicum.server.stats;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class ViewStats implements Serializable {
    private String app;
    private String uri;
    private Long hits;

    public ViewStats(String app, String uri, Long hits) {
        this.app = app;
        this.uri = uri;
        this.hits = hits;
    }
}