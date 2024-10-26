package com.schedule_planner.rest.configuration;

import com.schedule_planner.rest.config.ApplicationProperties;
import com.schedule_planner.gateway.email.EmailConnector;
import com.schedule_planner.rest.config.EmailProperties;
import com.schedule_planner.gateway.email.EmailConnectorImpl;
import com.schedule_planner.core.create_account.CreateAccountService;
import com.schedule_planner.core.create_account.SendVerificationEmail;
import com.schedule_planner.core.create_account.SendVerificationEmailImpl;
import com.schedule_planner.core.login.LoginService;
import com.schedule_planner.core.verified_account.VerifyAccountService;
import com.schedule_planner.encrypt.Encrypt;
import com.schedule_planner.encrypt.EncryptImpl;
import com.schedule_planner.encrypt.TokenService;
import com.schedule_planner.encrypt.TokenServiceImpl;
import com.schedule_planner.rest.config.JwtProperties;
import com.schedule_planner.store.AccountRepository;
import com.schedule_planner.store.AccountService;
import com.schedule_planner.store.AccountServiceImpl;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthenticationConfiguration {

    @Bean
    public CreateAccountService createAccountBusinessLogic(AccountService accountService, Encrypt encrypt, SendVerificationEmail sendVerification, TokenService tokenService) {
        return new CreateAccountService(accountService, encrypt, sendVerification, tokenService);
    }

    @Bean
    public LoginService loginBusinessLogic(AccountService accountService, Encrypt encrypt, TokenService tokenService) {
        return new LoginService(accountService, encrypt, tokenService);
    }

    @Bean
    public VerifyAccountService verifyAccountBusinessLogic(AccountService accountService, TokenService tokenService) {
        return new VerifyAccountService(accountService,tokenService);
    }

    @Bean
    public AccountService accountService(AccountRepository accountRepository) {
        return new AccountServiceImpl(accountRepository);
    }


    @Bean
    public Encrypt encrypt() {
        return new EncryptImpl();
    }

    @Bean
    public TokenService tokenService() {
        return new TokenServiceImpl(jwtProperties());
    }

    @Bean
    public SendVerificationEmail sendVerificationEmail(EmailConnector emailConnector, ApplicationProperties applicationConfig) {
        return new SendVerificationEmailImpl(emailConnector,applicationConfig);
    }

    @Bean
    public EmailConnector emailConnector(EmailProperties emailProperties) {
        return new EmailConnectorImpl(emailProperties);
    }


    @Bean
    @ConfigurationProperties(prefix = "jwt")
    public JwtProperties jwtProperties() {
        return new JwtProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "email")
    public EmailProperties emailProperties() {
        return new EmailProperties();
    }


    @Bean
    @ConfigurationProperties(prefix = "application")
    public ApplicationProperties applicationName() {
        return new ApplicationProperties();
    }

}
