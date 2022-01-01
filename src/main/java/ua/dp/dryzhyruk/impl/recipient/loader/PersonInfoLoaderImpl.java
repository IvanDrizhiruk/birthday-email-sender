package ua.dp.dryzhyruk.impl.recipient.loader;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;
import ua.dp.dryzhyruk.ports.recipient.loader.LoadRecipientsException;
import ua.dp.dryzhyruk.ports.recipient.loader.PersonInfoLoader;
import ua.dp.dryzhyruk.ports.recipient.loader.Recipient;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class PersonInfoLoaderImpl implements PersonInfoLoader {

    private final ResourceLoader resourceLoader;
    private final String recipientsFilePath;

    @Autowired
    public PersonInfoLoaderImpl(
            ResourceLoader resourceLoader,
            @Value("${recipients.file.path}") String recipientsFilePath) {
        this.resourceLoader = resourceLoader;
        this.recipientsFilePath = recipientsFilePath;
    }

    public List<Recipient> loadPersonInformation() throws LoadRecipientsException {
        log.info("Load recipient list from {}", recipientsFilePath);
        try (ICsvBeanReader csvBeanReader = prepareCsvBeanReader(recipientsFilePath)) {
            final String[] header = csvBeanReader.getHeader(true);
            final CellProcessor[] processors = prepareProcessors();

            List<Recipient> recipients = new ArrayList<>();

            RecipientCsvEntity recipient;
            while ((recipient = csvBeanReader.read(RecipientCsvEntity.class, header, processors)) != null) {
                recipients.add(toRecipient(recipient));
            }

            return recipients;
        } catch (Exception e) {
            throw new LoadRecipientsException("Unable parse file " + recipientsFilePath, e);
        }
    }

    private CsvBeanReader prepareCsvBeanReader(String recipientsFilePath) throws IOException {
        Resource resource = resourceLoader.getResource(recipientsFilePath);
        return new CsvBeanReader(new FileReader(resource.getFile()), CsvPreference.STANDARD_PREFERENCE);
    }

    private Recipient toRecipient(RecipientCsvEntity csvRecipientEntity) {
        return Recipient.builder()
                .dateOfBirth(csvRecipientEntity.getBirthday())
                .recipientFullName(csvRecipientEntity.getFullName())
                .recipientEmail(csvRecipientEntity.getEmail())
                .managerEmail(csvRecipientEntity.getManagerEmail())
                .build();
    }

    private static CellProcessor[] prepareProcessors() {
        return new CellProcessor[]{
                new ParseLocalDate(),
                new NotNull(),
                new NotNull(),
                new NotNull()
        };
    }
}
