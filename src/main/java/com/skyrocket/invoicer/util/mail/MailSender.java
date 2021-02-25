package com.skyrocket.invoicer.util.mail;

import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

@Component
@Slf4j
public class MailSender {

    @Value("${mail.username}")
    private String username;

    private String password = "^>YcMQ57b<~bg6B\\";

    @Value("${mail.smtpServer}")
    private String smtpServer;

    /**
     * sends an email with the subject and the (html) content to all email addresses from the recipient list
     * @param recipientList the list of recipients and blind recipients
     * @param subject the email's subject line
     * @param htmlMessage the email's content in HTML format
     * @throws MessagingException
     */
    public void sendMessage(RecipientList recipientList, String subject, String htmlMessage) throws MessagingException {
        Message message = new MimeMessage(getSession());
        message.setFrom(new InternetAddress(username));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("mit19629@spengergasse.at"));
        //message.setRecipients(Message.RecipientType.BCC, recipientList.getBlindRecipientsAsAddresses());
        message.setSubject(subject);
        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(htmlMessage, "text/html");
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);
        message.setContent(multipart);
        Transport.send(message);
        log.info("sent email to [{}] recipients and to [{}] blind recipients", recipientList.getRecipients().size(), recipientList.getBlindRecipients().size());
    }

    private Session getSession() {
        log.error("username [{}], password [{}], smtp [{}]", username, password, smtpServer);
        return Session.getInstance(getPropertiesForMail(), new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }

    private Properties getPropertiesForMail() {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", smtpServer);
        properties.put("mail.smtp.port", "587");
        return properties;
    }
    
}
