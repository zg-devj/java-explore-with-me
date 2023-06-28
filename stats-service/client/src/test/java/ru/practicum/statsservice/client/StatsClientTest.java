package ru.practicum.statsservice.client;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.practicum.statsservice.library.EndpointHitDto;

import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
class StatsClientTest {


    @InjectMocks
    private StatsClient statsClient;

    @Test
    public void test() {
        EndpointHitDto hitDto = new EndpointHitDto();
        hitDto.setApp("app");
        hitDto.setIp("127.0.0.1");
        hitDto.setTimestamp(LocalDateTime.now());
        hitDto.setUri("/events");
        ResponseEntity<EndpointHitDto> hit = statsClient.hit("http://localhost:9090", hitDto);
        System.out.println(hit.getBody());
    }
}