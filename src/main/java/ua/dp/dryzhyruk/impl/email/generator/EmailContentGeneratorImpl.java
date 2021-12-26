package ua.dp.dryzhyruk.impl.email.generator;

import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import ua.dp.dryzhyruk.email.generator.EmailContent;
import ua.dp.dryzhyruk.email.generator.EmailContentGenerator;
import ua.dp.dryzhyruk.recipient.group.EmailData;

import java.util.Map;

@Slf4j
@Service
public class EmailContentGeneratorImpl implements EmailContentGenerator {

    private final Configuration fmConfiguration;

    @Autowired
    public EmailContentGeneratorImpl(Configuration fmConfiguration) {
        this.fmConfiguration = fmConfiguration;
    }

    @Override
    public EmailContent generateEmailContent(EmailData emailData) {

        String html;
        switch (emailData.getType()) {
            case BIRTHDAY:
                html = generateHtmlContentForBirthday(emailData);
                break;
            default:
                throw new UnsupportedOperationException("Unsupported email type: " + emailData.getType());
        }

        return EmailContent.builder()
                .htmlContent(html)
                .build();
    }

    private String generateHtmlContentForBirthday(EmailData emailData) {
        Map<String, Object> model = Map.of(
                "recipientFullName", emailData.getTo().getRecipientFullName());
        return geContentFromTemplate("birthday.html", model);
    }

    @SneakyThrows
    public String geContentFromTemplate(String templateName, Map<String, Object> model) {
        Template template = fmConfiguration.getTemplate(templateName);

        return FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
    }
}
