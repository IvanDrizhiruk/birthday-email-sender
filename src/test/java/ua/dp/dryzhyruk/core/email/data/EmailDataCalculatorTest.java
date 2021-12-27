package ua.dp.dryzhyruk.core.email.data;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import ua.dp.dryzhyruk.ClockUtils;
import ua.dp.dryzhyruk.core.recipient.loader.Recipient;

import java.time.Clock;
import java.time.LocalDate;
import java.util.List;

class EmailDataCalculatorTest {

    @Test
    void persons_for_current_day_should_be_selected_for_birthday_emails() {
        //given
        Clock nowClock = ClockUtils.fixedClock("2021-06-25T16:45:42.00Z");

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

        List<Recipient> personsInformation = List.of(
                recipient1,
                recipient2,
                recipient3
        );

        List<EmailData> expected = List.of(
                EmailData.builder()
                        .type(EmailType.BIRTHDAY)
                        .to(recipient1.toBuilder().build())
                        .build(),
                EmailData.builder()
                        .type(EmailType.BIRTHDAY)
                        .to(recipient2.toBuilder().build())
                        .build()
        );

        //when
        List<EmailData> actual = new EmailDataCalculator(nowClock)
                .prepareEmailsData(personsInformation);

        //then
        Assertions.assertThat(actual)
                .containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    void for_current_day_monday_person_for_previouse_weekends_should_be_selected_for_birthday_emails() {
        //given
        Clock nowClock = ClockUtils.fixedClock("2021-12-27T16:45:42.00Z");

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

        List<EmailData> expected = List.of(
                EmailData.builder()
                        .type(EmailType.BIRTHDAY)
                        .to(recipient1.toBuilder().build())
                        .build(),
                EmailData.builder()
                        .type(EmailType.BIRTHDAY)
                        .to(recipient2.toBuilder().build())
                        .build()
        );

        //when
        List<EmailData> actual = new EmailDataCalculator(nowClock)
                .prepareEmailsData(personsInformation);

        //then
        Assertions.assertThat(actual)
                .containsExactlyInAnyOrderElementsOf(expected);
    }

}