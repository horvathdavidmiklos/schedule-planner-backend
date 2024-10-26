package com.schedule_planner.core.create_account;
import com.schedule_planner.util.security.token.TokenPurpose;
import com.schedule_planner.rest.config.ApplicationProperties;
import com.schedule_planner.util.email.EmailConnector;
import com.schedule_planner.util.email.EmailData;

public class SendVerificationEmailImpl implements SendVerificationEmail {
    private final EmailConnector emailConnector;
    private final ApplicationProperties applicationConfig;

    public SendVerificationEmailImpl(EmailConnector emailConnector, ApplicationProperties applicationConfig) {
        this.emailConnector = emailConnector;
        this.applicationConfig = applicationConfig;
    }

    public void send(String username, String emailAddress,String token) {
        var email = new EmailData()
                .to(emailAddress)
                .subject("Verify Your Email Address")
                .body(getBody(getLink(username,token, TokenPurpose.EMAIL_VERIFICATION.getName()),username));
        emailConnector.send(email);
    }

    private String getLink(String username, String token, String purpose){
        return applicationConfig.getHost() + "/verify-email/" + token + "/" + username+"/"+purpose;
    }

    private String getBody(String link, String username) {
        return "Dear " + username + "\n" +
                "Thank you for registering with " + applicationConfig.getName() + "\n" +
                "To complete your registration and activate your account, please verify your email address by clicking the link below:\n" +
                link+ "\n" +
                "If you did not create an account with us, please ignore this email.\n\n" +
                "Thank you,\n" +
                applicationConfig.getName() + " team";
    }
}
