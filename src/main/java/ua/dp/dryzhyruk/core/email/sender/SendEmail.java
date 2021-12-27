package ua.dp.dryzhyruk.core.email.sender;

import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

public class SendEmail {

//    //    @Autowired
//    private static FreeMarkerConfigurer freemarkerConfigurer = freemarkerConfig();
//
//
//    public static FreeMarkerConfigurer freemarkerConfig() {
//        FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
////        freeMarkerConfigurer.setTemplateLoaderPath("mail-templates");
//
//        Configuration configuration = new Configuration(Configuration.VERSION_2_3_27);
//        TemplateLoader templateLoader = new ClassTemplateLoader(SendEmail.class, "/" + "mail-templates");
//        configuration.setTemplateLoader(templateLoader);
//        freeMarkerConfigurer.setConfiguration(configuration);
//        return freeMarkerConfigurer;
//    }
//
//    public static void main(String[] args) throws IOException {
//        send1();
////        send2();
//    }
//
//    private static void send1() throws IOException {
//        // Recipient's email ID needs to be mentioned.
//        String to = "ivan.dryzhyruk@dxc.com";
//        // Sender's email ID needs to be mentioned
//        String from = "no-reply@dxc.com";
//
//        // Assuming you are sending email from localhost
//        String host = "localhost";
//
//        // Get system properties
//        Properties properties = System.getProperties();
//
//        // Setup mail server
//        properties.setProperty("mail.smtp.host", host);
//
//        // Get the default Session object.
//        Session session = Session.getDefaultInstance(properties);
//
//        try {
//            // Create a default MimeMessage object.
//            MimeMessage message = new MimeMessage(session);
//
//            // Set From: header field of the header.
//            message.setFrom(new InternetAddress(from));
//
//            // Set To: header field of the header.
//            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
//
//            // Set Subject: header field
//            message.setSubject("This is the Mega Subject!");
//
//            // Now set the actual message
////            message.setText("<b>This is actual Mega Message</b>");
////            message.setContent("<b>This is actual Mega Message</b>", "text/html");
//
//            Map<String, Object> templateModel = Map.of(
//                    "recipientName", "Ivan",
//                    "text", "olala",
//                    "senderName", "no-reply"
//            );
//
//
//            Template freemarkerTemplate = freemarkerConfigurer.getConfiguration().getTemplate("template-freemarker.ftl");
//            String htmlBody = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerTemplate, templateModel);
//
//            System.out.println("htmlBody: " + htmlBody);
//
//            message.setContent(htmlBody, "text/html");
//
//
//            // Send message
//            Transport.send(message);
//            System.out.println("Sent message successfully....");
//        } catch (MessagingException | TemplateException mex) {
//            mex.printStackTrace();
//        }
//    }
//
//
//    private static void send2() {
//        // Recipient's email ID needs to be mentioned.
//        String to = "ivan.dryzhyruk@dxc.com";
//        // Sender's email ID needs to be mentioned
//        String from = "no-reply@dxc.com";
//
//        // Assuming you are sending email from localhost
//        String host = "localhost";
//
//        // Get system properties
//        Properties properties = System.getProperties();
//
//        // Setup mail server
//        properties.setProperty("mail.smtp.host", host);
//
//        // Get the default Session object.
//        Session session = Session.getDefaultInstance(properties);
//
//        try {
//            // Create a default MimeMessage object.
//            MimeMessage message = new MimeMessage(session);
//
//
//            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
//            helper.setFrom(from);
//            helper.setTo(to);
//            helper.setSubject("This is the Mega Subject!!!");
//            helper.setText("<b>This is actual Mega Message</b>", true);
////            @Value("classpath:/mail-logo.png")
////            private Resource resourceFile;
////            helper.addInline("attachment.png", resourceFile);
//
//
//            // Send message
//            Transport.send(message);
//            System.out.println("Sent message successfully....");
//        } catch (MessagingException mex) {
//            mex.printStackTrace();
//        }
//    }
}