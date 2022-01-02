package ua.dp.dryzhyruk.ports.email.data;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder(toBuilder = true)
public class EmailContent {

    String subject;
    String htmlContent;
    List<String> imagesAbsolutePaths;
}
