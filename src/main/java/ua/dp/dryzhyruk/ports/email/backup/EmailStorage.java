package ua.dp.dryzhyruk.ports.email.backup;

import ua.dp.dryzhyruk.ports.email.data.EmailData;

import java.util.List;

public interface EmailStorage {

    void store(EmailData emailData);

    List<EmailData> retrieve();

    void cleanupStorage(EmailData emailData);
}
