package ua.dp.dryzhyruk.core.email.content.generator;

import ua.dp.dryzhyruk.core.email.data.EmailData;

public interface EmailContentGenerator {

    EmailContent generateEmailContent(EmailData emailData);
}
