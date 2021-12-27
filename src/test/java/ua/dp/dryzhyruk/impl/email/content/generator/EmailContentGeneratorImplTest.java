package ua.dp.dryzhyruk.impl.email.content.generator;

import freemarker.template.Configuration;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.ResourceLoader;
import ua.dp.dryzhyruk.app.config.FreemarkerConfiguration;
import ua.dp.dryzhyruk.core.email.content.generator.EmailContent;
import ua.dp.dryzhyruk.core.email.data.EmailData;
import ua.dp.dryzhyruk.core.email.data.EmailType;
import ua.dp.dryzhyruk.core.recipient.loader.Recipient;

import java.time.LocalDate;

class EmailContentGeneratorImplTest {

    @Test
    void html_content_should_be_generated_from_template() {
        //given
        ResourceLoader resourceLoader = new FileSystemResourceLoader();
        Configuration configuration = new FreemarkerConfiguration()
                .newConfiguration("src/test/resources/template-for-test", resourceLoader);

        EmailData emailData = EmailData.builder()
                .type(EmailType.BIRTHDAY)
                .to(Recipient.builder()
                        .dateOfBirth(LocalDate.of(1986, 6, 25))
                        .recipientFullName("Ivan Dryzhyruk")
                        .recipientEmail("ivan.drizhiruk@gmail.com")
                        .managerEmail("ivan.drizhiruk-manager@gmail.com")
                        .build())
                .build();

        EmailContent expected = EmailContent.builder()
                .htmlContent("<!DOCTYPE html>\n" +
                        "<html>\n" +
                        "  <head>\n" +
                        "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n" +
                        "  </head>\n" +
                        "  <body>\n" +
                        "    <p>Hi Ivan Dryzhyruk</p>\n" +
                        "    <p>Congratulations with birthday</p>\n" +
                        "    <p>Regards,</p>\n" +
                        "    <p>\n" +
                        "        <img src=\"cid:attachment.png\" />\n" +
                        "    </p>\n" +
                        "  </body>\n" +
                        "</html>\n")
                .build();

        //when
        EmailContent actual = new EmailContentGeneratorImpl(configuration)
                .generateEmailContent(emailData);

        //then
        Assertions.assertThat(actual).isEqualTo(expected);
    }
}