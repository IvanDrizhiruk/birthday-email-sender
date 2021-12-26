package ua.dp.dryzhyruk.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.dp.dryzhyruk.core.SendProcess;

import javax.annotation.PostConstruct;

@Component
public class ExecutionStarter {

    private final SendProcess sendProcess;

    @Autowired
    public ExecutionStarter(SendProcess sendProcess) {
        this.sendProcess = sendProcess;
    }

    @PostConstruct
    private void init() {
        sendProcess.execute();
    }
}
