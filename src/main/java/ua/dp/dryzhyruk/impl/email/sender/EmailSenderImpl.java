package ua.dp.dryzhyruk.impl.email.sender;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import ua.dp.dryzhyruk.ports.email.data.EmailData;
import ua.dp.dryzhyruk.ports.email.sender.EmailSender;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

@Slf4j
@Service
public class EmailSenderImpl implements EmailSender {

    private static final String MAIL_SMTP_HOST = "mail.smtp.host";
    private static final String MAIL_SMTP_PORT = "mail.smtp.port";
    private static final String MAIL_SMTP_USERNAME = "mail.smtp.user.name";
    private static final String MAIL_SMTP_PASSWORD = "mail.smtp.user.password";

    private final Session session;

    private final String noReplyEmailAddress;

    public EmailSenderImpl(
            @Value("${email.no-reply.address}") String noReplyEmailAddress,
            @Value("${" + MAIL_SMTP_HOST + "}") String mailSmtpHost,
            @Value("${" + MAIL_SMTP_PORT + "}") int mailSmtpPort,
            @Value("${" + MAIL_SMTP_USERNAME + "}") String mailSmtpUserName,
            @Value("${" + MAIL_SMTP_PASSWORD + "}") String mailSmtpUserPassword) {

        this.noReplyEmailAddress = noReplyEmailAddress;

        Properties config = new Properties();
        config.put("mail.smtp.auth", "true");
        config.put("mail.smtp.starttls.enable", "true");
        config.put(MAIL_SMTP_HOST, mailSmtpHost);
        config.put(MAIL_SMTP_PORT, mailSmtpPort);

        this.session = Session.getInstance(config, new Authenticator() {

            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(mailSmtpUserName, mailSmtpUserPassword);
            }
        });
    }

    @Override
    public void sendEmail(EmailData emailData) {
        log.info("Try to send mail: \n" +
                        " to: {},\n" +
                        " subject: {}\n" +
                        " content: {}\n" +
                        " images: {}.",
                emailData.getTo(),
                emailData.getEmailContent().getSubject(),
                emailData.getEmailContent().getHtmlContent(),
                emailData.getEmailContent().getImagesAbsolutePaths());

        try {
            MimeMessage mimeMessage = prepareMimeMessage(emailData, session);

            Transport.send(mimeMessage);
        } catch (MessagingException e) {
            log.error("Error. Unable to process message", e);
        }
    }

    private MimeMessage prepareMimeMessage(EmailData emailData, Session session) throws MessagingException {
        MimeMessage mimeMessage = new MimeMessage(session);
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, StandardCharsets.UTF_8.name());
        message.setFrom(noReplyEmailAddress);
        message.setTo(emailData.getTo());
        if (emailData.getCc() != null) {
            message.setCc(emailData.getCc());
        }
        message.setSubject(emailData.getEmailContent().getSubject());
        message.setText(emailData.getEmailContent().getHtmlContent(), true);

        emailData.getEmailContent().getImagesAbsolutePaths()
                .forEach(
                        imageAbsolutePath -> inlineImage(message, imageAbsolutePath));

        return mimeMessage;
    }

    @SneakyThrows
    private void inlineImage(MimeMessageHelper message, String imageAbsolutePath) {
        File imageFile = new File(imageAbsolutePath);
        String imageName = imageFile.getName();

        message.addInline(imageName, imageFile);
    }
}
