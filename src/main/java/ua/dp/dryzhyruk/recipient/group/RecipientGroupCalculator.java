package ua.dp.dryzhyruk.recipient.group;

import org.springframework.stereotype.Service;
import ua.dp.dryzhyruk.person.info.loader.PersonInformation;

import java.util.List;

@Service
public class RecipientGroupCalculator {

    public List<RecipientGroup> prepareRecipientGroups(List<PersonInformation> personsInformation) {
        return List.of();
    }
}
