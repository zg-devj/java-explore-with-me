package ru.practicum.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.practicum.common.EndpointHitDto;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatsClient {

    private final RestTemplate rest;

    public StatsClient(RestTemplate rest) {
        this.rest = rest;
    }

    @Value("${stats-service.server.url}")
    private String serverUrl;

    public ResponseEntity<Object> hit(EndpointHitDto body) {
        String url = serverUrl + "/hit";
        HttpEntity<Object> requestEntity = new HttpEntity<>(body);
        return rest.exchange(url, HttpMethod.POST, requestEntity, Object.class);
    }

    public ResponseEntity<Object> stats(LocalDateTime start, LocalDateTime end,
                                        boolean unique, List<String> uris,
                                        int page, int size) {
        String url = serverUrl + "/stats?start={start}&end={end}&unique={unique}&uris={uris}&page={page}&size={size}";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("start", URLEncoder.encode(start.format(formatter), StandardCharsets.UTF_8));
        parameters.put("end", URLEncoder.encode(end.format(formatter), StandardCharsets.UTF_8));
        parameters.put("unique", unique);
        // если отправить uris как инициированный пустой список size=0,
        // то на сервере статистики придет список с size=1,
        if (uris == null || uris.isEmpty()) {
            uris = null;
        }
        parameters.put("uris", uris);
        parameters.put("page", page);
        parameters.put("size", size);

        return rest.exchange(url, HttpMethod.GET, null, Object.class, parameters);
    }
}
