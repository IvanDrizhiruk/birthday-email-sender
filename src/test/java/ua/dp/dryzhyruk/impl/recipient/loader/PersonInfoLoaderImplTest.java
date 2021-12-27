package ua.dp.dryzhyruk.impl.recipient.loader;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import ua.dp.dryzhyruk.core.recipient.loader.LoadRecipientsException;
import ua.dp.dryzhyruk.core.recipient.loader.Recipient;

import java.time.LocalDate;
import java.util.List;

class PersonInfoLoaderImplTest {

    @Test
    void file_with_correct_data_should_be_loaded_and_parsed() throws LoadRecipientsException {
        //given
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        String recipientsFilePath = "recipients-for-test.csv";

        Recipient recipient1 = Recipient.builder()
                .dateOfBirth(LocalDate.of(1986, 6, 25))
                .recipientFullName("Ivan Dryzhyruk")
                .recipientEmail("ivan.drizhiruk@gmail.com")
                .managerEmail("ivan.drizhiruk-manager@gmail.com")
                .build();
        Recipient recipient2 = Recipient.builder()
                .dateOfBirth(LocalDate.of(2000, 6, 25))
                .recipientFullName("Mihail Fake")
                .recipientEmail("mihail.fake@gmail.com")
                .managerEmail("mihail.fake-manager@gmail.com")
                .build();
        Recipient recipient3 = Recipient.builder()
                .dateOfBirth(LocalDate.of(1990, 12, 26))
                .recipientFullName("Alexander Fake")
                .recipientEmail("alexander.fake@gmail.com")
                .managerEmail("alexander.fake-manager@gmail.com")
                .build();
        List<Recipient> expected = List.of(
                recipient1,
                recipient2,
                recipient3
        );

        //when
        List<Recipient> actual = new PersonInfoLoaderImpl(resourceLoader, recipientsFilePath)
                .loadPersonInformation();

        //then
        Assertions.assertThat(actual)
                .containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    void exception_should_be_thrown_in_case_empty_cell() {
        //given
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        String recipientsFilePath = "recipients-for-test-empty-fields.csv";

        //then
        org.junit.jupiter.api.Assertions.assertThrows(LoadRecipientsException.class, () -> {
            //when
            new PersonInfoLoaderImpl(resourceLoader, recipientsFilePath)
                    .loadPersonInformation();
        });
    }

    @Test
    void exception_should_be_thrown_in_case_unparsable_date() {
        //given
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        String recipientsFilePath = "recipients-for-test-unpassable-date.csv";

        //then
        org.junit.jupiter.api.Assertions.assertThrows(LoadRecipientsException.class, () -> {
            //when
            new PersonInfoLoaderImpl(resourceLoader, recipientsFilePath)
                    .loadPersonInformation();
        });
    }
}