package com.scheduleplanner.core.createaccount;

import com.scheduleplanner.rest.config.ApplicationProperties;
import com.scheduleplanner.core.mock.EmailConnectorFake;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SendVerificationEmailImplTest {
    private SendVerificationEmailImpl sendVerificationEmail;
    private EmailConnectorFake emailConnectorFake;

    @BeforeEach
    void setUp() {
        emailConnectorFake = new EmailConnectorFake();
        ApplicationProperties applicationConfig = new ApplicationProperties();
        applicationConfig.setHost("[HOST]");
        applicationConfig.setName("[NAME]");
        sendVerificationEmail = new SendVerificationEmailImpl(emailConnectorFake, applicationConfig);
    }

    @Test
    void send() {
        sendVerificationEmail.send("[USERNAME]", "EMAIL", "[LINK]");
        assertThat(emailConnectorFake.inputEmailData.to()).isEqualTo("EMAIL");
        String body = """
                Dear [USERNAME]
                Thank you for registering with [NAME]
                To complete your registration and activate your account, please verify your email address by clicking the link below:
                https://[HOST]:8443/schedule-planner/account/verify-email/[LINK]/[USERNAME]
                If you did not create an account with us, please ignore this email.
                
                Thank you,
                [NAME] team""";
        assertThat(emailConnectorFake.inputEmailData.body()).isEqualTo(body);
        assertThat(emailConnectorFake.inputEmailData.subject()).isEqualTo("Verify Your Email Address");

    }

}