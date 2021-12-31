package ua.dp.dryzhyruk.ports.email.data;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class EmailData {
    String to;
    EmailContent emailContent;
}
