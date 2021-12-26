package ua.dp.dryzhyruk.email.generator;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class EmailContent {

    String htmlContent;
}
