package ua.dp.dryzhyruk.core.email.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ua.dp.dryzhyruk.core.test.mode.TestModeController;
import ua.dp.dryzhyruk.ports.email.data.EmailContent;
import ua.dp.dryzhyruk.ports.email.data.EmailData;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PingEmailDataCalculator {

    private final TestModeController testModeController;
    private final Clock clock;
    private String pingRecipientEmailAddress;

    @Autowired
    public PingEmailDataCalculator(
            TestModeController testModeController,
            Clock clock,
            @Value("${ping.recipient.email.address}") String pingRecipientEmailAddress) {
        this.testModeController = testModeController;
        this.clock = clock;
        this.pingRecipientEmailAddress = pingRecipientEmailAddress;
    }

    public List<EmailData> prepareEmails() {
        if (testModeController.isTestMode()) {
            return List.of();
        }

        LocalDateTime now = LocalDateTime.now(clock);

        EmailContent emailContent = EmailContent.builder()
                .subject("Report birthday email send by " + now)
                .htmlContent(" Email prepared: " + now)
                .build();

        EmailData emailData = EmailData.builder()
                .to(pingRecipientEmailAddress)
                .emailContent(emailContent)
                .build();

        return List.of(emailData);
    }
}