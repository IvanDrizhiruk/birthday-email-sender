package ua.dp.dryzhyruk.ports.email.sender;

import ua.dp.dryzhyruk.ports.email.data.EmailData;

public interface EmailSender {

    void sendEmail(EmailData emailData);
}
