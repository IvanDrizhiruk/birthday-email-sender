package ua.dp.dryzhyruk.impl.email.content.generator;

import freemarker.template.Template;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import ua.dp.dryzhyruk.ports.email.content.generator.EmailContentGenerator;
import ua.dp.dryzhyruk.ports.email.data.EmailContent;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class FreemarkerEmailContentGenerator implements EmailContentGenerator {

    private final FreemarkerTemplateResourcesLoader freemarkerTemplateResourcesLoader;

    @Autowired
    public FreemarkerEmailContentGenerator(
            FreemarkerTemplateResourcesLoader freemarkerTemplateResourcesLoader) {
        this.freemarkerTemplateResourcesLoader = freemarkerTemplateResourcesLoader;
    }

    @SneakyThrows
    @Override
    public EmailContent generateFromTemplate(String templateName, Map<String, Object> model) {

        Template template = freemarkerTemplateResourcesLoader.getTemplate(templateName);

        List<String> imagesPaths = freemarkerTemplateResourcesLoader.getFilePathsFromDir("images");

        String htmlContent = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

        return EmailContent.builder()
                .subject("This is the Mega Subject!!!")
                .htmlContent(htmlContent)
                .images(imagesPaths)
                .build();
    }
}
