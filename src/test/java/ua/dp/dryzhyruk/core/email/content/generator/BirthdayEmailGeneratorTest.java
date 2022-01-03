package ua.dp.dryzhyruk.core.email.content.generator;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ua.dp.dryzhyruk.ports.email.content.generator.EmailContentGenerator;
import ua.dp.dryzhyruk.ports.email.data.EmailContent;
import ua.dp.dryzhyruk.ports.recipient.loader.Recipient;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

class BirthdayEmailGeneratorTest {

    @Test
    void html_content_should_be_generated_from_template() {
        //given
        EmailContentGenerator emailContentGenerator = Mockito.mock(EmailContentGenerator.class);
        Recipient recipient = Recipient.builder()
                .dateOfBirth(LocalDate.of(1986, 6, 25))
                .recipientFullName("Ivan Dryzhyruk")
                .recipientEmail("ivan.drizhiruk@gmail.com")
                .managerEmail("ivan.drizhiruk-manager@gmail.com")
                .build();

        EmailContent emailContent = EmailContent.builder()
                .subject("Subject")
                .htmlContent("Content")
                .imagesAbsolutePaths(List.of("file1.png"))
                .build();
        Mockito.when(emailContentGenerator.generateFromTemplate(
                        Mockito.anyString(), Mockito.anyString(), Mockito.anyMap()))
                .thenReturn(emailContent);

        Map<String, Object> expectedModel = Map.of(
                "recipientFullName", "Ivan Dryzhyruk");

        EmailContent expected = emailContent.toBuilder().build();

        //when
        EmailContent actual = new BirthdayEmailGenerator(emailContentGenerator)
                .generate(recipient);

        //then
        Mockito.verify(emailContentGenerator).generateFromTemplate(
                "birthdaySubject.txt",
                "birthdayContent.html",
                expectedModel);
        Assertions.assertThat(actual).isEqualTo(expected);
    }
}