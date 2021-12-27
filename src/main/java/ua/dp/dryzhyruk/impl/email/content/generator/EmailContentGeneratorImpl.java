package ua.dp.dryzhyruk.impl.email.content.generator;

import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import ua.dp.dryzhyruk.core.email.content.generator.EmailContent;
import ua.dp.dryzhyruk.core.email.content.generator.EmailContentGenerator;
import ua.dp.dryzhyruk.core.email.data.EmailData;

import java.util.Map;

@Slf4j
@Service
public class EmailContentGeneratorImpl implements EmailContentGenerator {

    private final Configuration freemarkerConfiguration;

    @Autowired
    public EmailContentGeneratorImpl(
            @Qualifier("freemarkerConfiguration")Configuration freemarkerConfiguration) {
        this.freemarkerConfiguration = freemarkerConfiguration;
    }

    @Override
    public EmailContent generateEmailContent(EmailData emailData) {
        return EmailContent.builder()
                .htmlContent(generateHtmlContent(emailData))
                .build();
    }

    private String generateHtmlContent(EmailData emailData) {
        switch (emailData.getType()) {
            case BIRTHDAY:
                return generateHtmlContentForBirthday(emailData);
            case WEEKLY_FOR_MANAGER: //TODO
            default:
                throw new UnsupportedOperationException("Unsupported email type: " + emailData.getType());
        }
    }

    private String generateHtmlContentForBirthday(EmailData emailData) {
        Map<String, Object> model = Map.of(
                "recipientFullName", emailData.getTo().getRecipientFullName());
        return generateFromTemplate("birthday.html", model);
    }

    @SneakyThrows
    private String generateFromTemplate(String templateName, Map<String, Object> model) {
        Template template = freemarkerConfiguration.getTemplate(templateName);

        return FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
    }
}
