package com.scheduleplanner.rest;

import com.scheduleplanner.common.email.EmailProperties;
import com.scheduleplanner.core.createaccount.CreateAccountBusinessLogic;
import com.scheduleplanner.gateway.store.AccountHandler;
import com.scheduleplanner.gateway.store.AccountHandlerImpl;
import com.scheduleplanner.core.login.LoginBusinessLogic;
import com.scheduleplanner.secret.Encrypt;
import com.scheduleplanner.secret.EncryptImpl;
import com.scheduleplanner.secret.TokenService;
import com.scheduleplanner.secret.TokenServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class AuthenticationConfig {

    @Bean
    public CreateAccountBusinessLogic createAccountBusinessLogic(AccountHandler accountHandler, Encrypt encrypt, EmailProperties emailProperties) {
        return new CreateAccountBusinessLogic(accountHandler,encrypt, emailProperties);
    }

    @Bean
    public LoginBusinessLogic loginBusinessLogic(AccountHandlerImpl accountHandler, Encrypt encrypt,TokenService tokenService) {
        return new LoginBusinessLogic(accountHandler, encrypt,tokenService);
    }

    @Bean
    public AccountHandlerImpl accountHandler(DataSource dataSource) {
        return new AccountHandlerImpl(dataSource);
    }

    @Bean
    public Encrypt encrypt(){
        return new EncryptImpl();
    }

    @Bean
    public TokenService tokenService(){
        return new TokenServiceImpl();
    }
}
