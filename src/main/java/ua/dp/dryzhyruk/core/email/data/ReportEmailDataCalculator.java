package ua.dp.dryzhyruk.core.email.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ua.dp.dryzhyruk.ports.email.data.EmailContent;
import ua.dp.dryzhyruk.ports.email.data.EmailData;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReportEmailDataCalculator {

    private final Clock clock;
    private final String reportRecipientEmailAddress;

    @Autowired
    public ReportEmailDataCalculator(
            Clock clock,
            @Value("${report.recipient.email.address}") String reportRecipientEmailAddress) {
        this.clock = clock;
        this.reportRecipientEmailAddress = reportRecipientEmailAddress;
    }

    public List<EmailData> prepareEmails(SentReport sentReport) {
        LocalDateTime now = LocalDateTime.now(clock);

        //TODO rework with template
        EmailContent emailContent = EmailContent.builder()
                .subject("Report birthday email send by " + now)
                .htmlContent("Email prepared: " + now + " " + sentReport)
                .build();

        EmailData emailData = EmailData.builder()
                .to(reportRecipientEmailAddress)
                .emailContent(emailContent)
                .build();

        return List.of(emailData);
    }
}