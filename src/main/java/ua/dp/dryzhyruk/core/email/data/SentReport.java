package ua.dp.dryzhyruk.core.email.data;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;

@Value
@Builder
public class SentReport {
    LocalDateTime sendingProcessStarted;
    int numberLoadedRecipients;
    List<String> birthdayMailsSentTo;
    LocalDateTime sendingProcessFinished;
}
