package ua.dp.dryzhyruk.core.email.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.dp.dryzhyruk.core.email.content.generator.BirthdayEmailGenerator;
import ua.dp.dryzhyruk.core.recipient.loader.Recipient;

import java.time.Clock;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmailDataCalculator {

    private final BirthdayEmailGenerator birthdayEmailGenerator;
    private final Clock clock;

    @Autowired
    public EmailDataCalculator(
            BirthdayEmailGenerator birthdayEmailGenerator,
            Clock clock) {
        this.birthdayEmailGenerator = birthdayEmailGenerator;
        this.clock = clock;
    }

    public List<EmailData> prepareEmails(List<Recipient> personsInformation) {
        LocalDate now = LocalDate.now(clock);

        return personsInformation.stream()
                .filter(recipient -> isCurrentOrPreviousWeekendsBirthday(now, recipient))
                .map(recipient -> EmailData.builder()
                        .to(recipient)
                        .emailContent(birthdayEmailGenerator.generate(recipient))
                        .build())
                .collect(Collectors.toList());
    }

    private boolean isCurrentOrPreviousWeekendsBirthday(LocalDate now, Recipient recipient) {
        if (isCurrentDayBirthday(now, recipient)) {
            return true;
        }

        boolean isDayMondayToday = now.getDayOfWeek() == DayOfWeek.MONDAY;
        if (!isDayMondayToday) {
            return false;
        }

        LocalDate sunday = now.minusDays(1);
        LocalDate saturday = now.minusDays(2);
        return isCurrentDayBirthday(sunday, recipient) || isCurrentDayBirthday(saturday, recipient);

        //TODO adв case for 02.29 -> send on next day
    }

    private boolean isCurrentDayBirthday(LocalDate now, Recipient recipient) {
        return recipient.getDateOfBirth().getMonth() == now.getMonth()
                && recipient.getDateOfBirth().getDayOfMonth() == now.getDayOfMonth();
    }
}
