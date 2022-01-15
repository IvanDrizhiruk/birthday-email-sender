package ua.dp.dryzhyruk.core.email.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.dp.dryzhyruk.core.email.content.generator.BirthdayEmailGenerator;
import ua.dp.dryzhyruk.core.test.mode.TestModeController;
import ua.dp.dryzhyruk.ports.email.data.EmailContent;
import ua.dp.dryzhyruk.ports.email.data.EmailData;
import ua.dp.dryzhyruk.ports.recipient.loader.Recipient;

import java.time.Clock;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BirthdayEmailDataCalculator {

    private final BirthdayEmailGenerator birthdayEmailGenerator;
    private final TestModeController testModeController;
    private final Clock clock;

    @Autowired
    public BirthdayEmailDataCalculator(
            BirthdayEmailGenerator birthdayEmailGenerator,
            TestModeController testModeController,
            Clock clock) {
        this.birthdayEmailGenerator = birthdayEmailGenerator;
        this.testModeController = testModeController;
        this.clock = clock;
    }

    public List<EmailData> prepareEmails(List<Recipient> recipients) {
        LocalDate now = LocalDate.now(clock);

        return recipients.stream()
                .filter(recipient -> testModeController.isTestMode()
                        ? testModeController.isRecipientInTestMode(recipient.getRecipientEmail())
                        : isCurrentAndNotWeekendsBirthday(now, recipient))
                .map(recipient -> {
                    EmailContent emailContent = birthdayEmailGenerator.generate(recipient);

                    String cc = testModeController.isTestMode() || emailContent.getAdditionalParameters() == null
                            ? null
                            : emailContent.getAdditionalParameters().get("email.cc");

                    return EmailData.builder()
                            .to(recipient.getRecipientEmail())
                            .cc(cc)
                            .emailContent(emailContent)
                            .build();
                })
                .collect(Collectors.toList());
    }

    private boolean isCurrentAndNotWeekendsBirthday(LocalDate now, Recipient recipient) {
        Recipient recipientNormalised = normaliseRecipientForNotLeapYear29OfFebruary(now, recipient);

        boolean isWeekend = now.getDayOfWeek() == DayOfWeek.SATURDAY || now.getDayOfWeek() == DayOfWeek.SUNDAY;
        if (isWeekend) {
            return false;
        }
        if (isCurrentDayBirthday(now, recipientNormalised)) {
            return true;
        }

        boolean isDayMondayToday = now.getDayOfWeek() == DayOfWeek.MONDAY;
        if (!isDayMondayToday) {
            return false;
        }

        LocalDate sunday = now.minusDays(1);
        LocalDate saturday = now.minusDays(2);
        boolean isBirthdayOnPreviousWeekends = isCurrentDayBirthday(sunday, recipientNormalised)
                || isCurrentDayBirthday(saturday, recipientNormalised);

        return isBirthdayOnPreviousWeekends;
    }

    private Recipient normaliseRecipientForNotLeapYear29OfFebruary(LocalDate now, Recipient recipient) {
        if (!now.isLeapYear()
                && recipient.getDateOfBirth().getMonth() == Month.FEBRUARY
                && recipient.getDateOfBirth().getDayOfMonth() == 29) {
            return recipient.toBuilder()
                    .dateOfBirth(recipient.getDateOfBirth().plusDays(1))
                    .build();
        }
        return recipient;
    }

    private boolean isCurrentDayBirthday(LocalDate now, Recipient recipient) {
        return recipient.getDateOfBirth().getMonth() == now.getMonth()
                && recipient.getDateOfBirth().getDayOfMonth() == now.getDayOfMonth();
    }
}