package ua.dp.dryzhyruk.ports.email.content.generator;

import ua.dp.dryzhyruk.ports.email.data.EmailContent;

import java.util.Map;

public interface EmailContentGenerator {

    EmailContent generateFromTemplate(
            String templateSubjectName,
            String templateContentName,
            Map<String, Object> model);
}
