package com.scheduleplanner.core.createaccount;

import com.scheduleplanner.rest.config.ApplicationProperties;
import com.scheduleplanner.gateway.email.EmailConnector;
import com.scheduleplanner.gateway.email.EmailData;

public class SendVerificationEmailImpl implements SendVerificationEmail {
    private final EmailConnector emailConnector;
    private final ApplicationProperties applicationConfig;

    public SendVerificationEmailImpl(EmailConnector emailConnector, ApplicationProperties applicationConfig) {
        this.emailConnector = emailConnector;
        this.applicationConfig = applicationConfig;
    }

    public void send(String username, String emailAddress, String verificationLink) {
        var email = new EmailData()
                .to(emailAddress)
                .subject("Verify Your Email Address")
                .body(createEmailBody(username, verificationLink));
        emailConnector.send(email);
    }

    private String createEmailBody(String username, String token) {
        return "Dear " + username + "\n" +
                "Thank you for registering with " + applicationConfig.getName() + "\n" +
                "To complete your registration and activate your account, please verify your email address by clicking the link below:\n" +
                "https://" + applicationConfig.getHost() + ":8443/schedule-planner/account/verify-email/" + token + "/" + username + "\n" +
                "If you did not create an account with us, please ignore this email.\n\n" +
                "Thank you,\n" +
                applicationConfig.getName() + " team";
    }
}
