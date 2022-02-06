package ua.dp.dryzhyruk.impl.email.backup;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ua.dp.dryzhyruk.ports.email.backup.EmailStorage;
import ua.dp.dryzhyruk.ports.email.data.EmailData;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.nio.charset.StandardCharsets.UTF_8;

@Slf4j
@Service
public class EmailStorageImpl implements EmailStorage {

    private final String emailBackupPath;
    private final Gson gson;

    @SneakyThrows
    public EmailStorageImpl(
            @Value("${email.backup.dir:email-backup}") String emailBackupPath) {
        this.emailBackupPath = emailBackupPath;
        Files.createDirectories(Paths.get(emailBackupPath));

        //TODO posibly extract to separate class
        this.gson = new GsonBuilder().create();
    }

    @Override
    public void store(EmailData emailData) {
        Objects.requireNonNull(emailData);

        String backupFileName = prepareBackupFileName(emailData);
        String backupContentAsJson = toJson(emailData);

        Path filePath = Paths.get(emailBackupPath, backupFileName);

        writeBackup(filePath, backupContentAsJson);
    }

    private String prepareBackupFileName(EmailData emailData) {
        return emailData.getTo() + ".json";
    }

    @SneakyThrows
    @Override
    public List<EmailData> retrieve() {
        try (Stream<Path> list = Files.list(Paths.get(emailBackupPath))) {
            return list
                    .map(this::loadFile)
                    .filter(Objects::nonNull)
                    .map(json -> fromJson(json, EmailData.class))
                    .collect(Collectors.toList());
        }
    }

    @Override
    public void cleanupStorage(EmailData emailData) {
        String backupFileName = prepareBackupFileName(emailData);
        Path filePath = Paths.get(emailBackupPath, backupFileName);
        removeFile(filePath);
    }

    private String toJson(EmailData emailData) {
        return gson.toJson(emailData);
    }

    private <T> T fromJson(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }

    private void writeBackup(Path filePath, String backupContent) {
        try (BufferedWriter writer = Files.newBufferedWriter(filePath, UTF_8)) {
            writer.append(backupContent);
        } catch (IOException e) {
            log.error("Unable write backup email", e);
        }
    }

    private String loadFile(Path path) {
        try {
            return Files.readString(path, UTF_8);
        } catch (IOException e) {
            log.error("Unable read file email: " + path, e);
            return null;
        }
    }

    private void removeFile(Path path) {
        try {
            Files.delete(path);
        } catch (Exception e) {
            log.error("Unable remove file email: " + path, e);
        }
    }
}
