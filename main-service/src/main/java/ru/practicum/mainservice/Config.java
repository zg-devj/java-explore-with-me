package ru.practicum.mainservice;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.practicum.statsservice.client.StatsClient;

@Configuration
public class Config {

    @Bean
    public StatsClient statsClient() {
        return new StatsClient();
    }
}
