package com.scheduleplanner.common.email;

import com.scheduleplanner.common.entity.EmailEntity;
import com.scheduleplanner.common.exception.baseexception.handled.EmailAddressNotFound;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Properties;

public class EmailSender {
    private final EmailEntity email;
    private final EmailProperties emailProperties;

    public EmailSender(EmailEntity email, EmailProperties emailProperties) {
        this.email = email;
        this.emailProperties = emailProperties;
    }

    public void send() {
        Session session = Session.getInstance(getProperties(),
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        System.out.println(emailProperties.getAddress()+" "+emailProperties.getPassword()+"-------------------------------------------------");
                        return new PasswordAuthentication(emailProperties.getAddress(), emailProperties.getPassword());
                    }
                });
        session.setDebug(true);
        try {
            Transport.send(getMessage(session));
            String timeStamp = LocalDateTime.now().format(new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd HH:mm:ss").toFormatter());
            System.out.println("email sent by techuser " + timeStamp);
        } catch (MessagingException e) {
            throw new EmailAddressNotFound("EMAIL_NOT_FOUND");
        }
    }

    private Properties getProperties() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");  // TLS
        props.put("mail.smtp.host", emailProperties.getHost());
        props.put("mail.smtp.port", "587");  // TLS port
        return props;
    }

    private Message getMessage(Session session) throws MessagingException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(emailProperties.getAddress()));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email.to()));
        message.setSubject(email.subject());
        message.setText(email.body());
        return message;
    }
}
