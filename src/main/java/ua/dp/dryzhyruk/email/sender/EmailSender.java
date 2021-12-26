package ua.dp.dryzhyruk.email.sender;

import ua.dp.dryzhyruk.email.generator.EmailContent;
import ua.dp.dryzhyruk.recipient.group.RecipientGroup;

public interface EmailSender {

    void sendEmail(RecipientGroup recipientGroup, EmailContent generatedEmail);
}
