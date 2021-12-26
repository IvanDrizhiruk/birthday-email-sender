package ua.dp.dryzhyruk.email.generator;

import ua.dp.dryzhyruk.recipient.group.EmailData;

public interface EmailContentGenerator {

    EmailContent generateEmailContent(EmailData emailData);
}
