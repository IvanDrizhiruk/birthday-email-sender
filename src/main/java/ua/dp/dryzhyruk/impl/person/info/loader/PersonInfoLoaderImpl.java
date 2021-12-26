package ua.dp.dryzhyruk.impl.person.info.loader;

import org.springframework.stereotype.Service;
import ua.dp.dryzhyruk.person.info.loader.PersonInfoLoader;
import ua.dp.dryzhyruk.person.info.loader.PersonInformation;

import java.util.List;

@Service
public class PersonInfoLoaderImpl implements PersonInfoLoader {

    public List<PersonInformation> loadPersonInformation() {
        return List.of();
    }
}
