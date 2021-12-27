package ua.dp.dryzhyruk.impl.email.sender;

import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import ua.dp.dryzhyruk.core.email.content.generator.EmailContent;
import ua.dp.dryzhyruk.core.email.sender.EmailSender;
import ua.dp.dryzhyruk.core.email.data.EmailData;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Slf4j
@Service
public class EmailSenderImpl implements EmailSender {

    @Override
    public void sendEmail(EmailData emailData, EmailContent emailContent) {
        String to = emailData.getTo().getRecipientEmail();
        String from = "no-reply@dxc.com";
        String host = "localhost";

        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", host);
        Session session = Session.getDefaultInstance(properties);

        try {
            MimeMessage mimeMessage = new MimeMessage(session);
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            message.setFrom(from);
            message.setTo(to);
            message.setSubject("This is the Mega Subject!!!"); //TODO
            message.setText(emailContent.getHtmlContent(), true);
//            @Value("classpath:/mail-logo.png")
//            private Resource resourceFile;
//            message.addInline("attachment.png", resourceFile);


            Transport.send(mimeMessage);
        } catch (MessagingException e) {
            log.error("Error", e);
        }
    }
}
