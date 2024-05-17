package lk.ijse.Util;
import javafx.scene.control.Alert;
import lk.ijse.model.Client;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.IOException;
import java.util.Properties;


public class SendEmail {


        // Sender's email and password
        private static Session newSession = null;
    public static void setUpServerProperties() {
            Properties properties = new Properties();
            properties.put("mail.smtp.port", "587"); // Use TLS port
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.starttls.enable", "true"); // Enable STARTTLS

            newSession = Session.getInstance(properties, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(
                            "danithahk@gmail.com", "hoyx jhjc byjs lncb");
                }
            });
        }

        public static void sendMail(String title, String subject, String body, String receiverMail, File pdfFile) throws MessagingException, IOException {
            setUpServerProperties();
            MimeMessage mimeMessage = draftMail(title, subject, body, receiverMail, pdfFile);
            sendMail(mimeMessage);
        }

        private static MimeMessage draftMail(String title, String sub, String body, String receiverMail, File pdfFile) throws MessagingException, IOException {
            MimeMessage mimeMessage = new MimeMessage(newSession);

            mimeMessage.setFrom(new InternetAddress("danithahk@gmail.com"));
            mimeMessage.addRecipients(Message.RecipientType.TO, receiverMail);
            mimeMessage.setSubject(sub);

            // Create the message part
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            String emailBody = "<h1>" + title + "</h1>" + "<p>" + body + "</p>";
            messageBodyPart.setContent(emailBody, "text/html");

            // Create a multipart message
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            // Attachment part
            if (pdfFile != null && pdfFile.exists()) {
                MimeBodyPart attachmentBodyPart = new MimeBodyPart();
                attachmentBodyPart.attachFile(pdfFile);
                multipart.addBodyPart(attachmentBodyPart);
            }

            // Send the complete message parts
            mimeMessage.setContent(multipart);

            return mimeMessage;
        }

        private static void sendMail(MimeMessage mimeMessage) throws MessagingException {
            String host = "smtp.gmail.com";
            Transport transport = newSession.getTransport("smtp");
            try {
                transport.connect(host, System.getenv("danithahk@gmail.com"), System.getenv("hoyx jhjc byjs lncb"));
                transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
                new Alert(Alert.AlertType.INFORMATION, "Email sent successfully!").show();
            } finally {
                transport.close();
            }
        }

}

