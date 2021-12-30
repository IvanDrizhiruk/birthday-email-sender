package ua.dp.dryzhyruk.core.email.content.generator;

import org.springframework.stereotype.Service;
import ua.dp.dryzhyruk.core.email.data.EmailContent;
import ua.dp.dryzhyruk.core.recipient.loader.Recipient;

import java.util.Map;

@Service
public class BirthdayEmailGenerator {

    private final EmailContentGenerator emailContentGenerator;

    public BirthdayEmailGenerator(EmailContentGenerator emailContentGenerator) {
        this.emailContentGenerator = emailContentGenerator;
    }

    public EmailContent generate(Recipient recipient) {
        Map<String, Object> model = Map.of(
                "recipientFullName", recipient.getRecipientFullName());

        return emailContentGenerator.generateFromTemplate("birthday.html", model);
    }
}
