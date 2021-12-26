package ua.dp.dryzhyruk.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import ua.dp.dryzhyruk.core.SendProcess;

import javax.annotation.PostConstruct;
import java.time.Clock;

@SpringBootApplication
//@PropertySource(value = {"application.properties"})
@ComponentScan(basePackages = {"ua.dp.dryzhyruk"})
public class BirthdayEmailSenderApplication {

    private final SendProcess sendProcess;

    public static void main(String[] args) {
        SpringApplication.run(BirthdayEmailSenderApplication.class, args);
    }

    public BirthdayEmailSenderApplication(SendProcess sendProcess) {
        this.sendProcess = sendProcess;
    }


    @PostConstruct
    private void init() {
        sendProcess.execute();
    }

    @Bean
    public Clock newClock() {
        return Clock.systemDefaultZone();
    }
}
