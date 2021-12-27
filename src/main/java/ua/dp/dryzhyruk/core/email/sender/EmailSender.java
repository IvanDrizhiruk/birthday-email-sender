package ua.dp.dryzhyruk.core.email.sender;

import ua.dp.dryzhyruk.core.email.content.generator.EmailContent;
import ua.dp.dryzhyruk.core.email.data.EmailData;

public interface EmailSender {

    void sendEmail(EmailData emailData, EmailContent generatedEmail);
}
