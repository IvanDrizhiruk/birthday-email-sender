package ua.dp.dryzhyruk.core.email.content.generator;

import ua.dp.dryzhyruk.core.email.data.EmailContent;

import java.util.Map;

public interface EmailContentGenerator {

    EmailContent generateFromTemplate(String templateName, Map<String, Object> model);
}
