package ua.dp.dryzhyruk.core.email.data;

import lombok.Builder;
import lombok.Value;
import org.springframework.core.io.Resource;

import java.util.List;

@Value
@Builder(toBuilder = true)
public class EmailContent {

    String htmlContent;
    List<Resource> images;
}
