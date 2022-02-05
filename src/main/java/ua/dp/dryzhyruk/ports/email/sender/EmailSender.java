package ua.dp.dryzhyruk.ports.email.sender;

import ua.dp.dryzhyruk.ports.email.data.EmailData;

public interface EmailSender {

    /**
     * Method send emeil according received emailData
     * @param emailData - data for send
     * @throws SendMailException - in case any issues with sending
     */
    void sendEmail(EmailData emailData);
}
