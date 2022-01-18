package ua.dp.dryzhyruk.impl.email.content.generator;

import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.SpringTemplateLoader;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FreemarkerTemplateResourcesLoader {

    private final String templatesDir;
    private final Configuration freemarkerConfiguration;
    private final ResourceLoader resourceLoader;

    public FreemarkerTemplateResourcesLoader(
            @Value("${birthday.email.templates.dir}") String templatesDir,
            ResourceLoader resourceLoader) {
        this.templatesDir = templatesDir;
        this.resourceLoader = resourceLoader;
        this.freemarkerConfiguration = newConfiguration(templatesDir, resourceLoader);
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
        File directoryWithImages = toAbsolutePath(new File(templatesDir, relativePath).getPath());

        if (!directoryWithImages.isDirectory()) {
            throw new IllegalArgumentException("Path is not directory " + directoryWithImages.getPath());
        }

        return Stream.of(Objects.requireNonNull(directoryWithImages.listFiles(File::isFile)))
                .map(File::getAbsolutePath)
                .sorted()
                .collect(Collectors.toList());
    }

    @SneakyThrows
    public Properties getAdditionalParameters() {
        Properties properties = new Properties();

        File fileWithAdditionalParameters = toAbsolutePath(new File(templatesDir, "additional.properties").getPath());
        try (FileInputStream propsFileStream = new FileInputStream(fileWithAdditionalParameters)) {
            properties.load(propsFileStream);
        }

        return properties;
    }

    //TODO rework
    private File toAbsolutePath(String recipientsFilePath) {
        try {
            File absoluteFile = resourceLoader
                    .getResource(recipientsFilePath)
                    .getFile()
                    .getAbsoluteFile();
            if (absoluteFile.exists()) {
                return absoluteFile;
            }
            return new File(recipientsFilePath)
                    .getAbsoluteFile();

        } catch (Exception e) {
            return new File(recipientsFilePath)
                    .getAbsoluteFile();
        }
    }
}
