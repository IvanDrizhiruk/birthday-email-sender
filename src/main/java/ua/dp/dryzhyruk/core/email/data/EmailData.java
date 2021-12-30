package ua.dp.dryzhyruk.core.email.data;

import lombok.Builder;
import lombok.Value;
import ua.dp.dryzhyruk.core.recipient.loader.Recipient;

@Value
@Builder
public class EmailData {
    Recipient to;

    EmailContent emailContent;

}
