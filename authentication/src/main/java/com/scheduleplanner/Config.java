package com.scheduleplanner;

import com.scheduleplanner.endpoint.createaccount.CreateAccountBusinessLogic;
import com.scheduleplanner.gateway.store.AccountHandler;
import com.scheduleplanner.endpoint.login.LoginBusinessLogic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class Config {

    @Bean
    public CreateAccountBusinessLogic createAccountBusinessLogic(AccountHandler accountHandler) {
        return new CreateAccountBusinessLogic(accountHandler);
    }

    @Bean
    public LoginBusinessLogic loginBusinessLogic(AccountHandler accountHandler) {
        return new LoginBusinessLogic(accountHandler);
    }

    @Bean
    public AccountHandler accountHandler(DataSource dataSource) {
        return new AccountHandler(dataSource);
    }

}
