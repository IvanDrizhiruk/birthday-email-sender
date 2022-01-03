package ua.dp.dryzhyruk.core.test.mode;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TestModeController {

    private final boolean isTestMode;
    private final String testModeRecipientEmail;

    public TestModeController(
            @Value("${test.mode}") boolean isTestMode,
            @Value("${test.recipient.email}") String testModeRecipientEmail) {
        this.isTestMode = isTestMode;
        this.testModeRecipientEmail = testModeRecipientEmail;
    }

    public boolean isTestMode() {
        return isTestMode;
    }

    public boolean isRecipientInTestMode(String recipientEmail) {
        return isTestMode && recipientEmail.equals(testModeRecipientEmail);
    }
}
