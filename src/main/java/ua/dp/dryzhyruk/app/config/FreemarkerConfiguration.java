package ua.dp.dryzhyruk.app.config;

import freemarker.template.Configuration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ResourceLoader;
import org.springframework.ui.freemarker.SpringTemplateLoader;

@org.springframework.context.annotation.Configuration
public class FreemarkerConfiguration {

    @Bean()
    @Qualifier("freemarkerConfiguration")
    public Configuration newConfiguration(
            @Value("${email.templates.dir}") String emailTemplatesDir,
            ResourceLoader resourceLoader) {
        Configuration config = new Configuration(Configuration.VERSION_2_3_31);
        config.setTemplateLoader(new SpringTemplateLoader(resourceLoader, emailTemplatesDir));

        return config;
    }
}
