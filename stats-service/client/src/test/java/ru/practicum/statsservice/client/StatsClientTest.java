package ru.practicum.statsservice.client;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class StatsClientTest {


//    @InjectMocks
//    private StatsClient statsClient;
//
//    @Test
//    public void test() {
//        EndpointHitDto hitDto = new EndpointHitDto();
//        hitDto.setApp("app");
//        hitDto.setIp("127.0.0.1");
//        hitDto.setTimestamp(LocalDateTime.now());
//        hitDto.setUri("/events");
//        ResponseEntity<EndpointHitDto> hit = statsClient.hit("http://localhost:9090", hitDto);
//        System.out.println(hit.getBody());
//    }
}