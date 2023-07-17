package ru.practicum.mainservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import ru.practicum.client.StatsClient;

@Configuration
public class Config {

    @Bean
    public StatsClient statsClient() {
        return new StatsClient(new RestTemplate());
    }
}
