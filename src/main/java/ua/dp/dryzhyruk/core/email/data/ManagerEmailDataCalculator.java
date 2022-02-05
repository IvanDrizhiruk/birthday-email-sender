package ua.dp.dryzhyruk.core.email.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.dp.dryzhyruk.core.email.content.generator.ManagerEmailGenerator;
import ua.dp.dryzhyruk.core.test.mode.TestModeController;
import ua.dp.dryzhyruk.ports.email.data.EmailData;
import ua.dp.dryzhyruk.ports.recipient.loader.Recipient;

import java.time.Clock;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ManagerEmailDataCalculator {

    private final ManagerEmailGenerator managerEmailGenerator;
    private final TestModeController testModeController;
    private final Clock clock;

    @Autowired
    public ManagerEmailDataCalculator(
            ManagerEmailGenerator birthdayEmailGenerator,
            TestModeController testModeController,
            Clock clock) {
        this.managerEmailGenerator = birthdayEmailGenerator;
        this.testModeController = testModeController;
        this.clock = clock;
    }

    public List<EmailData> prepareEmails(List<Recipient> recipients) {
        LocalDate now = LocalDate.now(clock);
        int nDaysBeforeMonthEnd = now.lengthOfMonth() - now.getDayOfMonth();
        boolean isLastDayOfMonth = now.lengthOfMonth() == now.getDayOfMonth();

        if ((nDaysBeforeMonthEnd <= 2 && now.getDayOfWeek() == DayOfWeek.FRIDAY)
                || isLastDayOfMonth) {


            Month nextMonth = now.with(TemporalAdjusters.firstDayOfNextMonth())
                    .getMonth();

            List<Recipient> recipientWithBirthdayInNextMonth = recipients.stream()
                    .filter(recipient -> testModeController.isTestMode()
                            ? testModeController.isRecipientInTestMode(recipient.getRecipientEmail())
                            : recipient.getDateOfBirth().getMonth() == nextMonth)
                    .collect(Collectors.toList());

            Map<String, List<Recipient>> recipientByManagers = recipientWithBirthdayInNextMonth.stream()
                    .collect(Collectors.groupingBy(Recipient::getManagerEmail));

            Map<String, Recipient> recipientByMail = recipients.stream()
                    .collect(Collectors.toMap(Recipient::getRecipientEmail, Function.identity()));

            //TODO exclude letter to me about me
            return recipientByManagers.entrySet().stream()
                    .map(mangerAndRecipientsWithBirthday -> EmailData.builder()
                        .to(mangerAndRecipientsWithBirthday.getKey())
                        .emailContent(managerEmailGenerator.generate(
                                recipientByMail.get(mangerAndRecipientsWithBirthday.getKey()),
                                Set.copyOf(mangerAndRecipientsWithBirthday.getValue())))
                        .build())
                    .collect(Collectors.toList());
        } else {
            return List.of();
        }
    }
}