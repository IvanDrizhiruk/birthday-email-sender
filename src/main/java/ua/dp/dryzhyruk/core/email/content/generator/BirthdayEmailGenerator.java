package ua.dp.dryzhyruk.core.email.content.generator;

import org.springframework.stereotype.Service;
import ua.dp.dryzhyruk.ports.email.data.EmailContent;
import ua.dp.dryzhyruk.ports.recipient.loader.Recipient;
import ua.dp.dryzhyruk.ports.email.content.generator.EmailContentGenerator;

import java.util.Map;

@Service
public class BirthdayEmailGenerator {

    public static final String BIRTHDAY_TEMPLATE_NAME = "birthday.html";
    public static final String PARAMETER_RECIPIENT_FULL_NAME = "recipientFullName";
    private final EmailContentGenerator emailContentGenerator;

    public BirthdayEmailGenerator(EmailContentGenerator emailContentGenerator) {
        this.emailContentGenerator = emailContentGenerator;
    }

    public EmailContent generate(Recipient recipient) {
        Map<String, Object> model = Map.of(
                PARAMETER_RECIPIENT_FULL_NAME, recipient.getRecipientFullName());

        return emailContentGenerator.generateFromTemplate(BIRTHDAY_TEMPLATE_NAME, model);
    }
}
