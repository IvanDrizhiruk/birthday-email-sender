package ua.dp.dryzhyruk.impl.email.generator;

import org.springframework.stereotype.Service;
import ua.dp.dryzhyruk.email.generator.EmailContent;
import ua.dp.dryzhyruk.email.generator.EmailContentGenerator;
import ua.dp.dryzhyruk.recipient.group.RecipientGroup;

@Service
public class EmailContentGeneratorImpl implements EmailContentGenerator {

    @Override
    public EmailContent generateEmailContent(RecipientGroup recipientGroup) {
        return EmailContent.builder().build();
    }
}
