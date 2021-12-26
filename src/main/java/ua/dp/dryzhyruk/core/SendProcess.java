package ua.dp.dryzhyruk.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.dp.dryzhyruk.email.generator.EmailContent;
import ua.dp.dryzhyruk.email.generator.EmailContentGenerator;
import ua.dp.dryzhyruk.email.sender.EmailSender;
import ua.dp.dryzhyruk.person.info.loader.PersonInfoLoader;
import ua.dp.dryzhyruk.person.info.loader.Recipient;
import ua.dp.dryzhyruk.recipient.group.EmailData;
import ua.dp.dryzhyruk.recipient.group.EmailDataCalculator;

import java.util.List;

@Service
public class SendProcess {

    private final PersonInfoLoader personInfoLoader;
    private final EmailDataCalculator emailRecipientCalculator;
    private final EmailContentGenerator emailContentGenerator;
    private final EmailSender emailSender;

    @Autowired
    public SendProcess(
            PersonInfoLoader personInfoLoader,
            EmailDataCalculator emailRecipientCalculator,
            EmailContentGenerator emailContentGenerator,
            EmailSender emailSender) {
        this.personInfoLoader = personInfoLoader;
        this.emailRecipientCalculator = emailRecipientCalculator;
        this.emailContentGenerator = emailContentGenerator;
        this.emailSender = emailSender;
    }

    public void execute() {
        List<Recipient> recipient = personInfoLoader.loadPersonInformation();
        List<EmailData> emailsData = emailRecipientCalculator.prepareEmailsData(recipient);

        emailsData.forEach(emailData -> {
            EmailContent generatedEmail = emailContentGenerator.generateEmailContent(emailData);
            emailSender.sendEmail(emailData, generatedEmail);
        });
    }
}
