package ua.dp.dryzhyruk.impl.email.sender;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import ua.dp.dryzhyruk.ports.email.data.EmailContent;
import ua.dp.dryzhyruk.ports.email.data.EmailData;
import ua.dp.dryzhyruk.ports.email.sender.EmailSender;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

@Slf4j
@Service
public class EmailSenderImpl implements EmailSender {

    public static final String LOCALHOST = "localhost";
    public static final String MAIL_SMTP_HOST = "mail.smtp.host";
    private final String noReplyEmailAddress;

    public EmailSenderImpl(@Value("${email.no-reply.address}") String noReplyEmailAddress) {
        this.noReplyEmailAddress = noReplyEmailAddress;
    }

    @Override
    public void sendEmail(EmailData emailData) {
        try {
            MimeMessage mimeMessage = prepareMimeMessage(
                    emailData.getEmailContent(),
                    emailData.getTo());

            Transport.send(mimeMessage);
        } catch (MessagingException e) {
            log.error("Error. Unable to process message", e);
        }
    }

    private MimeMessage prepareMimeMessage(EmailContent emailContent, String to) throws MessagingException {
        Properties properties = System.getProperties();
        properties.setProperty(MAIL_SMTP_HOST, LOCALHOST);
        Session session = Session.getDefaultInstance(properties);

        MimeMessage mimeMessage = new MimeMessage(session);
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, StandardCharsets.UTF_8.name());
        message.setFrom(noReplyEmailAddress);
        message.setTo(to);
        message.setSubject("This is the Mega Subject!!!"); //TODO
        message.setText(emailContent.getHtmlContent(), true);

        message.addInline("attachment.png", emailContent.getImages().get(0));


        return mimeMessage;
    }
}
