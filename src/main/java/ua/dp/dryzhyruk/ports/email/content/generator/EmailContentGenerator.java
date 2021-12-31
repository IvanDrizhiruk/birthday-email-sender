package ua.dp.dryzhyruk.ports.email.content.generator;

import ua.dp.dryzhyruk.ports.email.data.EmailContent;

import java.util.Map;

public interface EmailContentGenerator {

    EmailContent generateFromTemplate(String templateName, Map<String, Object> model);
}
