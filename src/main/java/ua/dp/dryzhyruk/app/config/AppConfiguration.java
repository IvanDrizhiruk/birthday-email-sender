package ua.dp.dryzhyruk.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
public class AppConfiguration {

    @Bean
    public Clock newClock() {
        return Clock.systemDefaultZone();
    }
}
