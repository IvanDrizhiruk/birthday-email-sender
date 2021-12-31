package ua.dp.dryzhyruk.ports.recipient.loader;

import java.util.List;

public interface PersonInfoLoader {

    List<Recipient> loadPersonInformation() throws LoadRecipientsException;
}
