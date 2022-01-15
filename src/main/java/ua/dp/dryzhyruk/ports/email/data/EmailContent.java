package ua.dp.dryzhyruk.ports.email.data;

import lombok.Builder;
import lombok.Value;

import java.util.List;
import java.util.Map;

@Value
@Builder(toBuilder = true)
public class EmailContent {

    String subject;
    String htmlContent;
    List<String> imagesAbsolutePaths;
    Map<String, String> additionalParameters;
}
