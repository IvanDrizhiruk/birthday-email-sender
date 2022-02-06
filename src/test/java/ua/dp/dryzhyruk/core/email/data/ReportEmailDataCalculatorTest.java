package ua.dp.dryzhyruk.core.email.data;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import ua.dp.dryzhyruk.ClockUtils;
import ua.dp.dryzhyruk.ports.email.data.EmailContent;
import ua.dp.dryzhyruk.ports.email.data.EmailData;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

class ReportEmailDataCalculatorTest {

    @Test
    void persons_for_current_day_should_be_selected_for_birthday_emails() {
        //given
        Clock nowClock = ClockUtils.fixedClock("2021-06-25T16:45:42.00Z");

        String reportRecipientEmailAddress = "ivan.drizhiruk@gmail.com";
        SentReport sentReport = SentReport.builder()
                .sendingProcessStarted(LocalDateTime.of(2022,2,6,12, 15))
                .numberLoadedRecipients(10)
                .birthdayMailsSentTo(List.of("mihail.fake@gmail.com"))
                .sendingProcessFinished(LocalDateTime.of(2022,2,6,12, 30))
                .build();

        EmailContent emailContent = EmailContent.builder()
                .subject("Report birthday email send by 2021-06-25T19:45:42")
                .htmlContent(
                        "Email prepared: 2021-06-25T19:45:42 " +
                        "SentReport(" +
                                "sendingProcessStarted=2022-02-06T12:15, " +
                                "numberLoadedRecipients=10, " +
                                "birthdayMailsSentTo=[mihail.fake@gmail.com], " +
                                "sendingProcessFinished=2022-02-06T12:30)")
                .build();

        List<EmailData> expected = List.of(
                EmailData.builder()
                        .to("ivan.drizhiruk@gmail.com")
                        .emailContent(emailContent)
                        .build()
        );

        //when
        List<EmailData> actual = new ReportEmailDataCalculator(nowClock, reportRecipientEmailAddress)
                .prepareEmails(sentReport);

        //then
        Assertions.assertThat(actual)
                .containsExactlyInAnyOrderElementsOf(expected);
    }
}