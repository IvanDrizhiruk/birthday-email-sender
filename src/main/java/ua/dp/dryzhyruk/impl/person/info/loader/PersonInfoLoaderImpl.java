package ua.dp.dryzhyruk.impl.person.info.loader;

import org.springframework.stereotype.Service;
import ua.dp.dryzhyruk.person.info.loader.PersonInfoLoader;
import ua.dp.dryzhyruk.person.info.loader.Recipient;

import java.time.LocalDate;
import java.util.List;

@Service
public class PersonInfoLoaderImpl implements PersonInfoLoader {

    public List<Recipient> loadPersonInformation() {

        Recipient person1 = Recipient.builder()
                .dateOfBirth(LocalDate.of(1986,6,25))
                .recipientFullName("Ivan Dryzhyruk")
                .recipientEmail("ivan.drizhiruk@gmail.com")
                .managerEmail("ivan.drizhiruk-manager@gmail.com")
                .build();
        
        return List.of(person1);
    }
}
