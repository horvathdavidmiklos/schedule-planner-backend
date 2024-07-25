package com.scheduleplanner;

import com.scheduleplanner.createaccount.CreateAccountBusinessLogic;
import com.scheduleplanner.dataaccesslayer.config.DatabaseProperties;
import com.scheduleplanner.dataaccesslayer.operations.authorization.AccountHandler;
import com.scheduleplanner.login.LoginBusinessLogic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
    public AccountHandler accountHandler() {
        return new AccountHandler(databaseProperties());
    }

    @Bean
    public DatabaseProperties databaseProperties() {
        return new DatabaseProperties();
    }
}
