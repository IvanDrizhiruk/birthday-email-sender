package ua.dp.dryzhyruk.app;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ua.dp.dryzhyruk.core.SendProcess;
import ua.dp.dryzhyruk.core.test.mode.TestModeController;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class ExecutionStarter {

    private final ApplicationContext ctx;
    private final SendProcess sendProcess;
    private final TestModeController testModeController;

    @Autowired
    public ExecutionStarter(
            ApplicationContext ctx,
            SendProcess sendProcess,
            TestModeController testModeController) {
        this.ctx = ctx;
        this.sendProcess = sendProcess;
        this.testModeController = testModeController;
    }

    @PostConstruct
    private void init() {
        if (testModeController.isTestMode()) {
            log.info("### Execution on start ###");
            sendProcess.sendMessages();

            terminateProgram();
        }
    }

    private void terminateProgram() {
        int exitCode = SpringApplication.exit(ctx, () -> 0);
        System.exit(exitCode);
    }

    @Scheduled(cron = "${email.sending.cron}")
    private void runRegulatory() {
        if (!testModeController.isTestMode()) {
            log.info("### Execution by scheduling ###");
            sendProcess.sendMessages();
        }
    }

    @Scheduled(cron = "${email.backup.resending.cron}")
    private void resendMailsFromBackup() {
        if (!testModeController.isTestMode()) {
            log.info("### Execution by scheduling ###");
            sendProcess.resendMessagesFromBackup();
        }
    }
}
