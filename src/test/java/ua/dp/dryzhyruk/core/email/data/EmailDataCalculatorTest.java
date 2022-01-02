package ua.dp.dryzhyruk.core.email.data;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ua.dp.dryzhyruk.ClockUtils;
import ua.dp.dryzhyruk.core.email.content.generator.BirthdayEmailGenerator;
import ua.dp.dryzhyruk.ports.email.data.EmailContent;
import ua.dp.dryzhyruk.ports.email.data.EmailData;
import ua.dp.dryzhyruk.ports.recipient.loader.Recipient;

import java.time.Clock;
import java.time.LocalDate;
import java.util.List;

class EmailDataCalculatorTest {

    @Test
    void persons_for_current_day_should_be_selected_for_birthday_emails() {
        //given
        Clock nowClock = ClockUtils.fixedClock("2021-06-25T16:45:42.00Z"); //friday
        BirthdayEmailGenerator birthdayEmailGenerator = Mockito.mock(BirthdayEmailGenerator.class);


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

        EmailContent emailContent1 = EmailContent.builder()
                .subject("This is the Mega Subject!!!")
                .htmlContent("recipient 1")
                .build();

        EmailContent emailContent2 = EmailContent.builder()
                .subject("This is the Mega Subject!!!")
                .htmlContent("recipient 2")
                .build();

        Mockito.when(birthdayEmailGenerator.generate(recipient1)).thenReturn(emailContent1);
        Mockito.when(birthdayEmailGenerator.generate(recipient2)).thenReturn(emailContent2);

        List<Recipient> personsInformation = List.of(
                recipient1,
                recipient2,
                recipient3
        );

        List<EmailData> expected = List.of(
                EmailData.builder()
                        .to("ivan.drizhiruk@gmail.com" )
                        .emailContent(emailContent1.toBuilder().build())
                        .build(),
                EmailData.builder()
                        .to("mihail.fake@gmail.com")
                        .emailContent(emailContent2.toBuilder().build())
                        .build()
        );

        //when
        List<EmailData> actual = new EmailDataCalculator(birthdayEmailGenerator, nowClock)
                .prepareEmails(personsInformation);

        //then
        Assertions.assertThat(actual)
                .containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    void for_current_day_monday_person_for_previouse_weekends_should_be_selected_for_birthday_emails() {
        //given
        Clock nowClock = ClockUtils.fixedClock("2021-12-27T16:45:42.00Z");
        BirthdayEmailGenerator birthdayEmailGenerator = Mockito.mock(BirthdayEmailGenerator.class);

        Recipient recipient1 = Recipient.builder()
                .dateOfBirth(LocalDate.of(2000, 12, 25))
                .recipientFullName("Mihail Fake")
                .recipientEmail("mihail.fake@gmail.com")
                .managerEmail("mihail.fake-manager@gmail.com")
                .build();

        Recipient recipient2 = Recipient.builder()
                .dateOfBirth(LocalDate.of(1990, 12, 26))
                .recipientFullName("Alexander Fake")
                .recipientEmail("alexander.fake@gmail.com")
                .managerEmail("alexander.fake-manager@gmail.com")
                .build();

        List<Recipient> personsInformation = List.of(
                recipient1,
                recipient2
        );

        EmailContent emailContent1 = EmailContent.builder()
                .htmlContent("recipient 1")
                .build();

        EmailContent emailContent2 = EmailContent.builder()
                .htmlContent("recipient 2")
                .build();

        Mockito.when(birthdayEmailGenerator.generate(recipient1)).thenReturn(emailContent1);
        Mockito.when(birthdayEmailGenerator.generate(recipient2)).thenReturn(emailContent2);

        List<EmailData> expected = List.of(
                EmailData.builder()
                        .to("mihail.fake@gmail.com")
                        .emailContent(emailContent1.toBuilder().build())
                        .build(),
                EmailData.builder()
                        .to("alexander.fake@gmail.com")
                        .emailContent(emailContent2.toBuilder().build())
                        .build()
        );

        //when
        List<EmailData> actual = new EmailDataCalculator(birthdayEmailGenerator, nowClock)
                .prepareEmails(personsInformation);

        //then
        Assertions.assertThat(actual)
                .containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    void person_should_not_be_selected_for_birthday_emails_on_weekends_sunday() {
        //given
        Clock nowClock = ClockUtils.fixedClock("2021-12-26T16:45:42.00Z");
        BirthdayEmailGenerator birthdayEmailGenerator = Mockito.mock(BirthdayEmailGenerator.class);

        Recipient recipient1 = Recipient.builder()
                .dateOfBirth(LocalDate.of(2000, 12, 25))
                .recipientFullName("Mihail Fake")
                .recipientEmail("mihail.fake@gmail.com")
                .managerEmail("mihail.fake-manager@gmail.com")
                .build();

        Recipient recipient2 = Recipient.builder()
                .dateOfBirth(LocalDate.of(1990, 12, 26))
                .recipientFullName("Alexander Fake")
                .recipientEmail("alexander.fake@gmail.com")
                .managerEmail("alexander.fake-manager@gmail.com")
                .build();

        List<Recipient> personsInformation = List.of(
                recipient1,
                recipient2
        );

        EmailContent emailContent1 = EmailContent.builder()
                .htmlContent("recipient 1")
                .build();

        EmailContent emailContent2 = EmailContent.builder()
                .htmlContent("recipient 2")
                .build();

        Mockito.when(birthdayEmailGenerator.generate(recipient1)).thenReturn(emailContent1);
        Mockito.when(birthdayEmailGenerator.generate(recipient2)).thenReturn(emailContent2);


        //when
        List<EmailData> actual = new EmailDataCalculator(birthdayEmailGenerator, nowClock)
                .prepareEmails(personsInformation);

        //then
        Assertions.assertThat(actual)
                .isEmpty();
    }

    @Test
    void person_should_not_be_selected_for_birthday_emails_on_weekends_saturday() {
        //given
        Clock nowClock = ClockUtils.fixedClock("2021-12-25T16:45:42.00Z");
        BirthdayEmailGenerator birthdayEmailGenerator = Mockito.mock(BirthdayEmailGenerator.class);

        Recipient recipient1 = Recipient.builder()
                .dateOfBirth(LocalDate.of(2000, 12, 25))
                .recipientFullName("Mihail Fake")
                .recipientEmail("mihail.fake@gmail.com")
                .managerEmail("mihail.fake-manager@gmail.com")
                .build();

        Recipient recipient2 = Recipient.builder()
                .dateOfBirth(LocalDate.of(1990, 12, 26))
                .recipientFullName("Alexander Fake")
                .recipientEmail("alexander.fake@gmail.com")
                .managerEmail("alexander.fake-manager@gmail.com")
                .build();

        List<Recipient> personsInformation = List.of(
                recipient1,
                recipient2
        );

        EmailContent emailContent1 = EmailContent.builder()
                .htmlContent("recipient 1")
                .build();

        EmailContent emailContent2 = EmailContent.builder()
                .htmlContent("recipient 2")
                .build();

        Mockito.when(birthdayEmailGenerator.generate(recipient1)).thenReturn(emailContent1);
        Mockito.when(birthdayEmailGenerator.generate(recipient2)).thenReturn(emailContent2);


        //when
        List<EmailData> actual = new EmailDataCalculator(birthdayEmailGenerator, nowClock)
                .prepareEmails(personsInformation);

        //then
        Assertions.assertThat(actual)
                .isEmpty();
    }
}