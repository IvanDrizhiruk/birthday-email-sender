package ua.dp.dryzhyruk.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.dp.dryzhyruk.email.generator.EmailContent;
import ua.dp.dryzhyruk.email.generator.EmailContentGenerator;
import ua.dp.dryzhyruk.email.sender.EmailSender;
import ua.dp.dryzhyruk.person.info.loader.PersonInfoLoader;
import ua.dp.dryzhyruk.person.info.loader.PersonInformation;
import ua.dp.dryzhyruk.recipient.group.RecipientGroup;
import ua.dp.dryzhyruk.recipient.group.RecipientGroupCalculator;

import java.util.List;

@Service
public class SendProcess {

    private final PersonInfoLoader personInfoLoader;
    private final RecipientGroupCalculator recipientGroupCalculator;
    private final EmailContentGenerator emailContentGenerator;
    private final EmailSender emailSender;

    @Autowired
    public SendProcess(
            PersonInfoLoader personInfoLoader,
            RecipientGroupCalculator recipientGroupCalculator,
            EmailContentGenerator emailContentGenerator,
            EmailSender emailSender) {
        this.personInfoLoader = personInfoLoader;
        this.recipientGroupCalculator = recipientGroupCalculator;
        this.emailContentGenerator = emailContentGenerator;
        this.emailSender = emailSender;
    }

    public void execute() {
        List<PersonInformation> personsInformation = personInfoLoader.loadPersonInformation();
        List<RecipientGroup> recipientGroups = recipientGroupCalculator.prepareRecipientGroups(personsInformation);

        recipientGroups.forEach(recipientGroup -> {
            EmailContent generatedEmail = emailContentGenerator.generateEmailContent(recipientGroup);
            emailSender.sendEmail(recipientGroup, generatedEmail);
        });
    }
}
