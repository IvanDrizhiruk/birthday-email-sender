package ua.dp.dryzhyruk.core;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.dp.dryzhyruk.core.email.data.EmailDataCalculator;
import ua.dp.dryzhyruk.ports.email.data.EmailData;
import ua.dp.dryzhyruk.ports.email.sender.EmailSender;
import ua.dp.dryzhyruk.ports.recipient.loader.PersonInfoLoader;
import ua.dp.dryzhyruk.ports.recipient.loader.Recipient;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
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
        log.info("Sending process started {}", LocalDateTime.now());
        List<Recipient> recipient = personInfoLoader.loadPersonInformation();

        log.info("Recipient loaded. Number of records: {}", recipient.size());
        List<EmailData> emailsData = emailRecipientCalculator.prepareEmails(recipient);

        log.info("Mails for send. Number of mails: {}", emailsData.size());
        emailsData.forEach(emailSender::sendEmail);

        log.info("Sending process finished {}", LocalDateTime.now());
    }
}
