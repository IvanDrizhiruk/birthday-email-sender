package ua.dp.dryzhyruk.impl.recipient.loader;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;
import ua.dp.dryzhyruk.core.recipient.loader.PersonInfoLoader;
import ua.dp.dryzhyruk.core.recipient.loader.Recipient;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class PersonInfoLoaderImpl implements PersonInfoLoader {

    @Autowired //TODO
    private ResourceLoader resourceLoader;

    @SneakyThrows
    public List<Recipient> loadPersonInformation() {
        List<Recipient> recipients = new ArrayList<>();

        try (ICsvBeanReader csvBeanReader = new CsvBeanReader(
                new FileReader(
                        resourceLoader.getResource("persons.csv").getFile()
                ),
                CsvPreference.STANDARD_PREFERENCE)) {
            final String[] header = csvBeanReader.getHeader(true);
            final CellProcessor[] processors = getProcessors();

            CsvRecipient employee = null;
            while ((employee = csvBeanReader.read(CsvRecipient.class, header, processors)) != null) {
                recipients.add(toRecipient(employee));
            }
        }

        return recipients;
    }

    private Recipient toRecipient(CsvRecipient csvRecipient) {
        return Recipient.builder()
                .dateOfBirth(csvRecipient.getBirthday())
                .recipientFullName(csvRecipient.getFullName())
                .recipientEmail(csvRecipient.getEmail())
                .managerEmail(csvRecipient.getManagerEmail())
                .build();
    }

    private static CellProcessor[] getProcessors() {
        return new CellProcessor[]{
                new ParseLocalDate(),
                new NotNull(),
                new NotNull(),
                new NotNull()
        };
    }
}
