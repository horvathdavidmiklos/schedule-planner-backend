package com.scheduleplanner.core.createaccount;

import com.scheduleplanner.common.entity.EmailEntity;
import com.scheduleplanner.common.gateway.EmailConnector;
import org.springframework.beans.factory.annotation.Autowired;

public class SendVerificationEmail {
    private final EmailConnector emailConnector;

    @Autowired
    private  String domainName;

    @Autowired
    private String applicationName;

    public SendVerificationEmail(EmailConnector emailConnector) {
        this.emailConnector = emailConnector;
    }

    public void send(String username, String emailAddress, String verificationLink) {
        var email = new EmailEntity()
                .to(emailAddress)
                .subject("Verify Your Email Address")
                .body(createEmailBody(username, verificationLink));
        emailConnector.send(email);
    }

    private String createEmailBody(String username, String verificationLink) {
        return "Dear " + username + "\n" +
                "Thank you for registering with" + applicationName + "\n" +
                "To complete your registration and activate your account, please verify your email address by clicking the link below:\n" +
                "https://"+domainName+":8443/schedule-planner/"+verificationLink +"\n"+ //TODO change verification link
                "If you did not create an account with us, please ignore this email.\n\n" +
                "Thank you,\n" +
                applicationName + " team";
    }
}
