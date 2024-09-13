package com.scheduleplanner.common.gateway;

import com.scheduleplanner.common.email.EmailConfig;
import com.scheduleplanner.common.entity.EmailEntity;
import com.scheduleplanner.common.exception.baseexception.handled.EmailAddressNotFound;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Properties;

public class EmailConnector {
    private final EmailConfig emailProperties;

    public EmailConnector(EmailConfig emailProperties) {
        this.emailProperties = emailProperties;
    }

    public void send(EmailEntity email) {
        Session session = Session.getInstance(getProperties(),
                new jakarta.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(emailProperties.getAddress(), emailProperties.getPassword());
                    }
                });
        session.setDebug(true);
        try {
            Transport.send(getMessage(session,email));
            String timeStamp = LocalDateTime.now().format(new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd HH:mm:ss").toFormatter());
            System.out.println("email sent by techuser " + timeStamp);
        } catch (MessagingException e) {
            throw new EmailAddressNotFound("EMAIL_NOT_FOUND");
        }
    }


    private Properties getProperties() {
        Properties props = new Properties();
        props.setProperty("mail.user", emailProperties.getAddress());
        props.setProperty("mail.password", emailProperties.getPassword());
        props.put("mail.smtp.auth", "true");
        props.put("mail.  smtp.starttls.enable", "true");  // TLS
        props.put("mail.smtp.host", emailProperties.getHost());   // Gmail SMTP szerver
        props.put("mail.smtp.port", "587");  // TLS port
        return props;
    }


    private Message getMessage(Session session,EmailEntity email) throws MessagingException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(emailProperties.getAddress()));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email.to()));
        message.setSubject(email.subject());
        message.setText(email.body());
        return message;
    }
}
