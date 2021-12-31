package ua.dp.dryzhyruk.impl.email.content.generator;

import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.SpringTemplateLoader;

import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FreemarkerTemplateResourcesLoader {

    private final String emailTemplatesDir;
    private final Configuration freemarkerConfiguration;
    private final ResourceLoader resourceLoader;

    public FreemarkerTemplateResourcesLoader(
            @Value("${email.templates.dir}") String emailTemplatesDir,
            ResourceLoader resourceLoader) {
        this.emailTemplatesDir = emailTemplatesDir;
        this.resourceLoader = resourceLoader;
        this.freemarkerConfiguration = newConfiguration(emailTemplatesDir, resourceLoader);
    }

    private Configuration newConfiguration(String emailTemplatesDir, ResourceLoader resourceLoader) {
        Configuration config = new Configuration(Configuration.VERSION_2_3_31);
        config.setTemplateLoader(new SpringTemplateLoader(resourceLoader, emailTemplatesDir));

        return config;
    }

    @SneakyThrows
    public Template getTemplate(String templateName) {
        return freemarkerConfiguration.getTemplate(templateName);
    }

    @SneakyThrows
    public List<String> getFilePathsFromDir(String relativePath) {
        File directoryWithImages = resourceLoader.getResource(new File(emailTemplatesDir, relativePath).getPath())
                .getFile();

        if (!directoryWithImages.isDirectory()) {
            throw new IllegalArgumentException("Path is not directory " + directoryWithImages.getPath());
        }

        return Stream.of(Objects.requireNonNull(directoryWithImages.listFiles(File::isFile)))
                .map(File::getAbsolutePath)
                .sorted()
                .collect(Collectors.toList());
    }
}
