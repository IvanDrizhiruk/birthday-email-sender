package ua.dp.dryzhyruk.person.info.loader;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder(toBuilder = true)
public class Recipient {

    LocalDate dateOfBirth;
    String recipientFullName;
    String recipientEmail;
    String managerEmail;
}
