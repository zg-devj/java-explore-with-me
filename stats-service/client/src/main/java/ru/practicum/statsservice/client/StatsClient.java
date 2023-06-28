package ru.practicum.statsservice.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.practicum.statsservice.library.EndpointHitDto;

@Service
public class StatsClient {

    @Value("${stats-service.server.url}")
    private String serverUrl;

    //    public ResponseEntity<EndpointHitDto> hit(@Value("${shareit-server.url}") String serverUrl, EndpointHitDto body) {
    public ResponseEntity<EndpointHitDto> hit(EndpointHitDto body) {
        RestTemplate rest = new RestTemplate();
        String url = serverUrl + "/hit";
        HttpEntity<EndpointHitDto> requestEntity = new HttpEntity<>(body);
        return rest.exchange(url, HttpMethod.POST, requestEntity, EndpointHitDto.class);
    }
}
