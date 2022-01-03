package ua.dp.dryzhyruk.impl.email.content.generator;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.ResourceLoader;
import ua.dp.dryzhyruk.ports.email.data.EmailContent;

import java.io.File;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class FreemarkerEmailContentGeneratorTest {

    @Test
    void html_content_should_be_generated_from_template() {
        //given
        String templateSubjectName = "birthdaySubject.txt";
        String templateContentName = "birthdayContent.html";

        ResourceLoader resourceLoader = new FileSystemResourceLoader();
        FreemarkerTemplateResourcesLoader resourcesLoader = new FreemarkerTemplateResourcesLoader(
                "src/test/resources/template-for-test", resourceLoader);

        Map<String, Object> model = Map.of(
                "recipientFullName", "Ivan Dryzhyruk");

        EmailContent expected = EmailContent.builder()
                .subject("Mega Happy birthday Ivan Dryzhyruk!!!")
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
                .imagesAbsolutePaths(Stream.of(
                                new File("src/test/resources/template-for-test/images/file1.png").getAbsolutePath(),
                                new File("src/test/resources/template-for-test/images/file2.gif").getAbsolutePath(),
                                new File("src/test/resources/template-for-test/images/file3.jpeg").getAbsolutePath())
                        .collect(Collectors.toList()))
                .build();

        //when
        EmailContent actual = new FreemarkerEmailContentGenerator(resourcesLoader)
                .generateFromTemplate(templateSubjectName, templateContentName, model);

        //then
        Assertions.assertThat(actual).isEqualTo(expected);
    }
}