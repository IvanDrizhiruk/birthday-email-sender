package ua.dp.dryzhyruk.email.sender;

import ua.dp.dryzhyruk.email.generator.EmailContent;
import ua.dp.dryzhyruk.recipient.group.EmailData;

public interface EmailSender {

    void sendEmail(EmailData emailData, EmailContent generatedEmail);
}
