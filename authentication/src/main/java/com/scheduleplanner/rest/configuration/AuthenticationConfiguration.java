package com.scheduleplanner.rest.configuration;

import com.scheduleplanner.rest.config.ApplicationProperties;
import com.scheduleplanner.gateway.email.EmailConnector;
import com.scheduleplanner.rest.config.EmailProperties;
import com.scheduleplanner.gateway.email.EmailConnectorImpl;
import com.scheduleplanner.core.createaccount.CreateAccountBusinessLogic;
import com.scheduleplanner.core.createaccount.SendVerificationEmail;
import com.scheduleplanner.core.createaccount.SendVerificationEmailImpl;
import com.scheduleplanner.core.login.LoginBusinessLogic;
import com.scheduleplanner.core.verifiedaccount.VerifyAccountBusinessLogic;
import com.scheduleplanner.encrypt.Encrypt;
import com.scheduleplanner.encrypt.EncryptImpl;
import com.scheduleplanner.encrypt.TokenService;
import com.scheduleplanner.encrypt.TokenServiceImpl;
import com.scheduleplanner.rest.config.JwtProperties;
import com.scheduleplanner.store.AccountRepository;
import com.scheduleplanner.store.AccountService;
import com.scheduleplanner.store.AccountServiceImpl;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthenticationConfiguration {

    @Bean
    public CreateAccountBusinessLogic createAccountBusinessLogic(AccountService accountService, Encrypt encrypt,SendVerificationEmail sendVerification, TokenService tokenService) {
        return new CreateAccountBusinessLogic(accountService, encrypt, sendVerification, tokenService);
    }

    @Bean
    public LoginBusinessLogic loginBusinessLogic(AccountService accountService, Encrypt encrypt, TokenService tokenService) {
        return new LoginBusinessLogic(accountService, encrypt, tokenService);
    }

    @Bean
    public VerifyAccountBusinessLogic verifyAccountBusinessLogic(AccountService accountService, TokenService tokenService) {
        return new VerifyAccountBusinessLogic(accountService,tokenService);
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
