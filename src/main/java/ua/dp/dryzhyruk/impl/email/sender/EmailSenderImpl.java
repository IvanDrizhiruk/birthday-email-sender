package ua.dp.dryzhyruk.impl.email.sender;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import ua.dp.dryzhyruk.ports.email.data.EmailData;
import ua.dp.dryzhyruk.ports.email.sender.EmailSender;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

@Slf4j
@Service
public class EmailSenderImpl implements EmailSender {

    public static final String MAIL_SMTP_HOST = "mail.smtp.host";
    public static final String LOCALHOST = "localhost";
    private final String noReplyEmailAddress;

    public EmailSenderImpl(
            @Value("${email.no-reply.address}") String noReplyEmailAddress) {
        this.noReplyEmailAddress = noReplyEmailAddress;
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
            MimeMessage mimeMessage = prepareMimeMessage(emailData);

            Transport.send(mimeMessage);
        } catch (MessagingException e) {
            log.error("Error. Unable to process message", e);
        }
    }

    private MimeMessage prepareMimeMessage(EmailData emailData) throws MessagingException {
        Properties properties = System.getProperties();
        properties.setProperty(MAIL_SMTP_HOST, LOCALHOST);
        Session session = Session.getDefaultInstance(properties);

        MimeMessage mimeMessage = new MimeMessage(session);
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, StandardCharsets.UTF_8.name());
        message.setFrom(noReplyEmailAddress);
        message.setTo(emailData.getTo());
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
