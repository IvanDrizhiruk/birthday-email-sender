package ua.dp.dryzhyruk.email.generator;

import ua.dp.dryzhyruk.recipient.group.RecipientGroup;

public interface EmailContentGenerator {

    EmailContent generateEmailContent(RecipientGroup recipientGroup);
}
