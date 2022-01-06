package ua.dp.dryzhyruk.core.email.content.generator;

import org.springframework.stereotype.Service;
import ua.dp.dryzhyruk.ports.email.content.generator.EmailContentGenerator;
import ua.dp.dryzhyruk.ports.email.data.EmailContent;
import ua.dp.dryzhyruk.ports.recipient.loader.Recipient;

import java.util.Map;
import java.util.Set;

@Service
public class ManagerEmailGenerator {

    public static final String MANAGER_SUBJECT_TEMPLATE_NAME = "managerSubject.txt";
    public static final String MANAGER_CONTENT_TEMPLATE_NAME = "managerContent.html";
    public static final String PARAMETER_MANAGER_FULL_NAME = "managerFullName";
    public static final String PARAMETER_RECIPIENTS_WITH_BIRTHDAY = "recipientsWithBirthday";
    private final EmailContentGenerator emailContentGenerator;

    public ManagerEmailGenerator(EmailContentGenerator emailContentGenerator) {
        this.emailContentGenerator = emailContentGenerator;
    }

    public EmailContent generate(Recipient recipient, Set<Recipient> recipientsWithBirthday) {
        Map<String, Object> model = Map.of(
                PARAMETER_MANAGER_FULL_NAME, recipient.getRecipientFullName(),
                PARAMETER_RECIPIENTS_WITH_BIRTHDAY, recipientsWithBirthday);

        return emailContentGenerator.generateFromTemplate(
                MANAGER_SUBJECT_TEMPLATE_NAME,
                MANAGER_CONTENT_TEMPLATE_NAME,
                model);
    }
}
