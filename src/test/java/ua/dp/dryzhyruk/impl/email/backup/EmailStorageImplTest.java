package ua.dp.dryzhyruk.impl.email.backup;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import ua.dp.dryzhyruk.ports.email.data.EmailContent;
import ua.dp.dryzhyruk.ports.email.data.EmailData;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

class EmailStorageImplTest {

    @Test
    void email_should_be_stored_in_file_and_retrieve() {
        //given
        EmailContent emailContent = EmailContent.builder()
                .subject("This is the Mega Subject!!!")
                .htmlContent("recipient 1")
                .build();

        EmailData emailData = EmailData.builder()
                .to("ivan.drizhiruk@gmail.com")
                .emailContent(emailContent)
                .build();

        EmailData expected = EmailData.builder()
                .to("ivan.drizhiruk@gmail.com")
                .emailContent(emailContent.toBuilder().build())
                .build();
        //when
        EmailStorageImpl emailStorage = new EmailStorageImpl("target/email-directory-1");
        emailStorage.store(emailData);
        List<EmailData> actual = emailStorage.retrieve();
        //then
        Assertions.assertThat(actual)
                .containsExactly(expected);
    }

    @Test
    void email_should_be_stored_in_file_and_retrieve_if_file_exist() throws IOException {
        //given
        String emailBackupPath = "target/email-directory-2";
        Files.createDirectories(Path.of(emailBackupPath));
        Files.writeString(Path.of(emailBackupPath, "ivan.drizhiruk@gmail.com.json"), "Some text", UTF_8);

        EmailContent emailContent = EmailContent.builder()
                .subject("This is the Mega Subject!!!")
                .htmlContent("recipient 1")
                .build();

        EmailData emailData = EmailData.builder()
                .to("ivan.drizhiruk@gmail.com")
                .emailContent(emailContent)
                .build();

        EmailData expected = EmailData.builder()
                .to("ivan.drizhiruk@gmail.com")
                .emailContent(emailContent.toBuilder().build())
                .build();

        //when
        EmailStorageImpl emailStorage = new EmailStorageImpl(emailBackupPath);
        emailStorage.store(emailData);
        List<EmailData> actual = emailStorage.retrieve();

        //then
        Assertions.assertThat(actual)
                .containsExactly(expected);
        long countOfFiles = Files.list(Paths.get(emailBackupPath))
                .count();
        Assertions.assertThat(countOfFiles)
                .isEqualTo(1L);
    }

    @Test
    void file_should_be_removed_on_clean() throws IOException {
        //given
        String emailBackupPath = "target/email-directory-3";
        EmailData emailData = EmailData.builder()
                .to("ivan.drizhiruk@gmail.com")
                .build();
        //when
        EmailStorageImpl emailStorage = new EmailStorageImpl(emailBackupPath);
        emailStorage.store(emailData);
        emailStorage.cleanupStorage(emailData);

        //then
        long countOfFiles = Files.list(Paths.get(emailBackupPath))
                .count();
        Assertions.assertThat(countOfFiles)
                .isZero();
    }
}