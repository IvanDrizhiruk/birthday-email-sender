package ua.dp.dryzhyruk.recipient.group;

import org.springframework.stereotype.Service;
import ua.dp.dryzhyruk.person.info.loader.Recipient;

import java.time.Clock;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmailDataCalculator {

    private final Clock clock;

    public EmailDataCalculator(Clock clock) {
        this.clock = clock;
    }

    public List<EmailData> prepareEmailsData(List<Recipient> personsInformation) {

        LocalDate now = LocalDate.now(clock);

        return prepareEmailsDataForBirthdayPersons(personsInformation, now);
    }

    private List<EmailData> prepareEmailsDataForBirthdayPersons(List<Recipient> personsInformation, LocalDate now) {

        return personsInformation.stream()
//                .filter(recipient -> isCurrentOrPreviousWeekendsBirthday(now, recipient))
                .map(recipient -> EmailData.builder()
                        .type(EmailType.BIRTHDAY)
                        .to(recipient)
                        .build())
                .collect(Collectors.toList());
    }

    private boolean isCurrentOrPreviousWeekendsBirthday(LocalDate now, Recipient recipient) {
        if(isCurrentDayBirthday(now, recipient)) {
            return true;
        }

        boolean isDayMondayToday = now.getDayOfWeek() == DayOfWeek.MONDAY;
        if (!isDayMondayToday) {
            return false;
        }

        LocalDate sunday = now.minusDays(1);
        LocalDate saturday = now.minusDays(2);
        return isCurrentDayBirthday(sunday, recipient) || isCurrentDayBirthday(saturday, recipient);

        //TODO ad case for 02.29 -> send on next day
    }

    private boolean isCurrentDayBirthday(LocalDate now, Recipient recipient) {
        return recipient.getDateOfBirth().getMonth() == now.getMonth()
                && recipient.getDateOfBirth().getDayOfMonth() == now.getDayOfMonth();
    }
}
