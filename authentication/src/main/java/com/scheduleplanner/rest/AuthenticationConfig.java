package com.scheduleplanner.rest;

import com.scheduleplanner.common.email.EmailConfig;
import com.scheduleplanner.common.gateway.EmailConnector;
import com.scheduleplanner.core.createaccount.CreateAccountBusinessLogic;
import com.scheduleplanner.core.createaccount.SendVerificationEmail;
import com.scheduleplanner.gateway.store.AccountRepository;
import com.scheduleplanner.gateway.store.AccountRepositoryImpl;
import com.scheduleplanner.core.login.LoginBusinessLogic;
import com.scheduleplanner.secret.Encrypt;
import com.scheduleplanner.secret.EncryptImpl;
import com.scheduleplanner.secret.TokenService;
import com.scheduleplanner.secret.TokenServiceImpl;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class AuthenticationConfig {

    @Bean
    public CreateAccountBusinessLogic createAccountBusinessLogic(AccountRepository accountRepository, Encrypt encrypt, EmailConfig emailProperties, SendVerificationEmail sendVerification, TokenService tokenService) {
        return new CreateAccountBusinessLogic(accountRepository, encrypt,sendVerification, tokenService);
    }

    @Bean
    public LoginBusinessLogic loginBusinessLogic(AccountRepositoryImpl accountRepository, Encrypt encrypt, TokenService tokenService) {
        return new LoginBusinessLogic(accountRepository, encrypt, tokenService);
    }

    @Bean
    public AccountRepositoryImpl accountRepository(DataSource dataSource) {
        return new AccountRepositoryImpl(dataSource);
    }

    @Bean
    public Encrypt encrypt() {
        return new EncryptImpl();
    }

    @Bean
    public TokenService tokenService() {
        return new TokenServiceImpl();
    }

    @Bean
    public SendVerificationEmail sendVerificationEmail(EmailConfig emailProperties, EmailConnector emailConnector) {
        return new SendVerificationEmail(emailConnector);
    }

    @Bean
    public EmailConnector emailConnector(EmailConfig emailProperties) {
        return new EmailConnector(emailProperties);
    }

    @Bean
    @ConfigurationProperties(prefix = "email")
    public EmailConfig emailProperties() {
        return new EmailConfig();
    }

    @Bean
    @ConfigurationProperties(prefix = "domain-name")
    public String domainName(){
        return "";
    }

    @Bean
    @ConfigurationProperties(prefix = "application-name")
    public String applicationName(){
        return "";
    }

}
