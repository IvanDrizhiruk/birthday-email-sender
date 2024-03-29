package ua.dp.dryzhyruk.core;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.dp.dryzhyruk.core.email.data.BirthdayEmailDataCalculator;
import ua.dp.dryzhyruk.core.email.data.ManagerEmailDataCalculator;
import ua.dp.dryzhyruk.core.email.data.ReportEmailDataCalculator;
import ua.dp.dryzhyruk.core.email.data.SentReport;
import ua.dp.dryzhyruk.ports.email.backup.EmailStorage;
import ua.dp.dryzhyruk.ports.email.data.EmailData;
import ua.dp.dryzhyruk.ports.email.sender.EmailSender;
import ua.dp.dryzhyruk.ports.email.sender.SendMailException;
import ua.dp.dryzhyruk.ports.recipient.loader.PersonInfoLoader;
import ua.dp.dryzhyruk.ports.recipient.loader.Recipient;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
public class SendProcess {

    private final PersonInfoLoader personInfoLoader;
    private final BirthdayEmailDataCalculator emailRecipientCalculator;
    private final ManagerEmailDataCalculator managerEmailDataCalculator;
    private final ReportEmailDataCalculator reportEmailDataCalculator;
    private final EmailSender emailSender;
    private final EmailStorage emailStorage;

    @Autowired
    public SendProcess(
            PersonInfoLoader personInfoLoader,
            BirthdayEmailDataCalculator emailRecipientCalculator,
            ManagerEmailDataCalculator managerEmailDataCalculator,
            ReportEmailDataCalculator reportEmailDataCalculator, EmailSender emailSender, EmailStorage emailStorage) {
        this.personInfoLoader = personInfoLoader;
        this.emailRecipientCalculator = emailRecipientCalculator;
        this.managerEmailDataCalculator = managerEmailDataCalculator;
        this.reportEmailDataCalculator = reportEmailDataCalculator;
        this.emailSender = emailSender;
        this.emailStorage = emailStorage;
    }

    @SneakyThrows
    public void sendMessages() {
        LocalDateTime sendingProcessStarted = LocalDateTime.now();
        log.info("Sending process started {}", sendingProcessStarted);
        List<Recipient> recipient = personInfoLoader.loadPersonInformation();

        log.info("Recipient loaded. Number of records: {}", recipient.size());
        List<EmailData> birthdayEmailsData = emailRecipientCalculator.prepareEmails(recipient);
        log.info("Birthday mails for send. Number of mails: {}", birthdayEmailsData.size());
        //TODO support manager mails
//        List<EmailData> managerEmailsData = managerEmailDataCalculator.prepareEmails(recipient);
//        log.info("Managers mails for send. Number of mails: {}", managerEmailsData.size());

        Stream.of(birthdayEmailsData/*, managerEmailsData*/)
                .flatMap(Collection::stream)
                .forEach(this::sendEmailOrSaveOnError);

        LocalDateTime sendingProcessFinished = LocalDateTime.now();
        log.info("Sending process finished {}", sendingProcessFinished);

        SentReport sentReport = SentReport.builder()
                .sendingProcessStarted(sendingProcessStarted)
                .numberLoadedRecipients(recipient.size())
                .birthdayMailsSentTo(birthdayEmailsData.stream()
                        .map(EmailData::getTo)
                        .collect(Collectors.toList()))
                .sendingProcessFinished(sendingProcessFinished)
                .build();
        List<EmailData> reportEmails = reportEmailDataCalculator.prepareEmails(sentReport);
        reportEmails
                .forEach(emailSender::sendEmail);
        log.info("Report mail sent");
    }

    private void sendEmailOrSaveOnError(EmailData emailData) {
        try {
            emailSender.sendEmail(emailData);
        } catch (SendMailException e) {
            log.error("Unable send message to " + emailData.getTo(), e);
            emailStorage.store(emailData);
        }
    }

    public void resendMessagesFromBackup() {
        emailStorage.retrieve().stream()
                .map(emailData -> {
                    try {
                        emailSender.sendEmail(emailData);

                        return emailData;
                    } catch (SendMailException e) {
                        log.error("Unable send message from backup to " + emailData.getTo(), e);
                        return null;
                    }
                })
                .forEach(emailStorage::cleanupStorage);
    }
}
