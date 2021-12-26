package ua.dp.dryzhyruk.recipient.group;

import lombok.Builder;
import lombok.Value;
import ua.dp.dryzhyruk.person.info.loader.Recipient;

@Value
@Builder
public class EmailData {
    EmailType type;
    Recipient to;
}
