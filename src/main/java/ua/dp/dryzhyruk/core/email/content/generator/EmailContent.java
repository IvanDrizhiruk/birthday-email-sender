package ua.dp.dryzhyruk.core.email.content.generator;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class EmailContent {

    String htmlContent;
}
