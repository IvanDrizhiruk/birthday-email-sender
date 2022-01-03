package ua.dp.dryzhyruk.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ua.dp.dryzhyruk.core.SendProcess;
import ua.dp.dryzhyruk.core.test.mode.TestModeController;

import javax.annotation.PostConstruct;

@Component
public class ExecutionStarter {

    private final SendProcess sendProcess;
    private final TestModeController testModeController;

    @Autowired
    public ExecutionStarter(
            SendProcess sendProcess,
            TestModeController testModeController) {
        this.sendProcess = sendProcess;
        this.testModeController = testModeController;
    }

    @PostConstruct
    private void init() {
        if (testModeController.isTestMode()) {
            sendProcess.execute();
            System.exit(0);
        }
    }

    @Scheduled(cron = "${email.sending.cron}")
    private void runRegulatory() {
        if (!testModeController.isTestMode()) {
            sendProcess.execute();
        }
    }
}
