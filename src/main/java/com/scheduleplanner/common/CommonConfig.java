package com.scheduleplanner.common;

import com.scheduleplanner.authorization.createaccount.CreateAccountBusinessLogic;
import com.scheduleplanner.authorization.login.LoginBusinessLogic;
import com.scheduleplanner.dataaccesslayer.config.DatabaseProperties;
import com.scheduleplanner.dataaccesslayer.operations.authorization.AccountHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class CommonConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

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
