package ua.dp.dryzhyruk.impl.email.content.generator;

import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import ua.dp.dryzhyruk.ports.email.content.generator.EmailContentGenerator;
import ua.dp.dryzhyruk.ports.email.data.EmailContent;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class EmailContentGeneratorImpl implements EmailContentGenerator {

    private final Configuration freemarkerConfiguration;
    private final ResourceLoader resourceLoader;

    @Autowired
    public EmailContentGeneratorImpl(
            @Qualifier("freemarkerConfiguration") Configuration freemarkerConfiguration,
            ResourceLoader resourceLoader) {
        this.freemarkerConfiguration = freemarkerConfiguration;
        this.resourceLoader = resourceLoader;
    }

    @SneakyThrows
    @Override
    public EmailContent generateFromTemplate(String templateName, Map<String, Object> model) {

        Resource resource = resourceLoader.getResource("templates/mail-logo.png");

        Template template = freemarkerConfiguration.getTemplate(templateName);

        String htmlContent = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

        return EmailContent.builder()
                .htmlContent(htmlContent)
                .images(List.of(resource))
                .build();
    }
}
