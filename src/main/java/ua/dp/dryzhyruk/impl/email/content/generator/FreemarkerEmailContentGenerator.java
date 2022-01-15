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
import java.util.stream.Collectors;

@Slf4j
@Service
public class FreemarkerEmailContentGenerator implements EmailContentGenerator {

    private static final String IMAGES_FOLDER_NAME = "images";
    private final FreemarkerTemplateResourcesLoader freemarkerTemplateResourcesLoader;

    @Autowired
    public FreemarkerEmailContentGenerator(
            FreemarkerTemplateResourcesLoader freemarkerTemplateResourcesLoader) {
        this.freemarkerTemplateResourcesLoader = freemarkerTemplateResourcesLoader;
    }

    @SneakyThrows
    @Override
    public EmailContent generateFromTemplate(
            String templateSubjectName,
            String templateContentName,
            Map<String, Object> model) {

        Template subjectTemplate = freemarkerTemplateResourcesLoader.getTemplate(templateSubjectName);
        String subject = FreeMarkerTemplateUtils.processTemplateIntoString(subjectTemplate, model);

        Template contentTemplate = freemarkerTemplateResourcesLoader.getTemplate(templateContentName);
        String htmlContent = FreeMarkerTemplateUtils.processTemplateIntoString(contentTemplate, model);

        List<String> imagesPaths = freemarkerTemplateResourcesLoader.getFilePathsFromDir(IMAGES_FOLDER_NAME);

        Map<String, String> additionalParameters = freemarkerTemplateResourcesLoader.getAdditionalParameters()
                .entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> entry.getKey().toString(),
                        entry -> entry.getValue().toString()));

        return EmailContent.builder()
                .subject(subject)
                .htmlContent(htmlContent)
                .imagesAbsolutePaths(imagesPaths)
                .additionalParameters(additionalParameters)
                .build();
    }
}
