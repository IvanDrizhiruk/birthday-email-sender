package ua.dp.dryzhyruk.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"ua.dp.dryzhyruk"})
public class BirthdayEmailSenderApplication {

    public static void main(String[] args) {
        SpringApplication.run(BirthdayEmailSenderApplication.class, args);
    }
}
