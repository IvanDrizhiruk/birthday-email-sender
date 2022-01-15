package ua.dp.dryzhyruk.core.email.content.generator;

import org.springframework.stereotype.Service;
import ua.dp.dryzhyruk.ports.email.content.generator.EmailContentGenerator;
import ua.dp.dryzhyruk.ports.email.data.EmailContent;
import ua.dp.dryzhyruk.ports.recipient.loader.Recipient;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Service
public class BirthdayEmailGenerator {

    public static final String BIRTHDAY_SUBJECT_TEMPLATE_NAME = "birthdaySubject.txt";
    public static final String BIRTHDAY_CONTENT_TEMPLATE_NAME = "birthdayContent.html";
    public static final String PARAMETER_RECIPIENT_FULL_NAME = "recipientFullName";
    public static final String PARAMETER_BIRTH_DAY_AND_MONTH = "birthDayAndMonth";
    public static final DateTimeFormatter FORMATTER_DAY_AND_MONTH = DateTimeFormatter.ofPattern("dd MMMM");

    private final EmailContentGenerator emailContentGenerator;

    public BirthdayEmailGenerator(EmailContentGenerator emailContentGenerator) {
        this.emailContentGenerator = emailContentGenerator;
    }

    public EmailContent generate(Recipient recipient) {
        Map<String, Object> model = Map.of(
                PARAMETER_RECIPIENT_FULL_NAME, recipient.getRecipientFullName(),
                PARAMETER_BIRTH_DAY_AND_MONTH, toDayAndMonth(recipient.getDateOfBirth()));

        return emailContentGenerator.generateFromTemplate(
                BIRTHDAY_SUBJECT_TEMPLATE_NAME,
                BIRTHDAY_CONTENT_TEMPLATE_NAME,
                model);
    }

    private String toDayAndMonth(LocalDate dateOfBirth) {
        return dateOfBirth.format(FORMATTER_DAY_AND_MONTH);
    }
}
