package ua.dp.dryzhyruk.impl.recipient.loader;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RecipientCsvEntity {
    private LocalDate birthday;
    private String fullName;
    private String email;
    private String managerEmail;
}
