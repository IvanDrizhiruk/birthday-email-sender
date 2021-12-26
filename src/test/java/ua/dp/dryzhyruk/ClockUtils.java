package ua.dp.dryzhyruk;

import lombok.SneakyThrows;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

public class ClockUtils {

    @SneakyThrows
    public static Clock fixedClock(String date) {
        return Clock.fixed(Instant.parse(date), ZoneId.systemDefault());
    }
}
