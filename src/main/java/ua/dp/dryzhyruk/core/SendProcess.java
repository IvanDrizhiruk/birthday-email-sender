package ua.dp.dryzhyruk.core;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.dp.dryzhyruk.ports.email.data.EmailData;
import ua.dp.dryzhyruk.core.email.data.EmailDataCalculator;
import ua.dp.dryzhyruk.ports.email.sender.EmailSender;
import ua.dp.dryzhyruk.ports.recipient.loader.PersonInfoLoader;
import ua.dp.dryzhyruk.ports.recipient.loader.Recipient;

import java.util.List;

@Service
public class SendProcess {

    private final PersonInfoLoader personInfoLoader;
    private final EmailDataCalculator emailRecipientCalculator;
    private final EmailSender emailSender;

    @Autowired
    public SendProcess(
            PersonInfoLoader personInfoLoader,
            EmailDataCalculator emailRecipientCalculator,
            EmailSender emailSender) {
        this.personInfoLoader = personInfoLoader;
        this.emailRecipientCalculator = emailRecipientCalculator;
        this.emailSender = emailSender;
    }

    @SneakyThrows
    public void execute() {
        List<Recipient> recipient = personInfoLoader.loadPersonInformation();
        List<EmailData> emailsData = emailRecipientCalculator.prepareEmails(recipient);
        emailsData.forEach(emailSender::sendEmail);
    }
}
