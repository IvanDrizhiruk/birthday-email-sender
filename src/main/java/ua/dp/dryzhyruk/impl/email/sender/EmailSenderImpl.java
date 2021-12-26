package ua.dp.dryzhyruk.impl.email.sender;

import org.springframework.stereotype.Service;
import ua.dp.dryzhyruk.email.generator.EmailContent;
import ua.dp.dryzhyruk.email.sender.EmailSender;
import ua.dp.dryzhyruk.recipient.group.RecipientGroup;

@Service
public class EmailSenderImpl implements EmailSender {

    @Override
    public void sendEmail(RecipientGroup recipientGroup, EmailContent generatedEmail) {

    }
}
