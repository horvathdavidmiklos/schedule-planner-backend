package com.scheduleplanner.rest;

import com.scheduleplanner.core.createaccount.CreateAccountBusinessLogic;
import com.scheduleplanner.core.createaccount.dto.AccountInDto;
import com.scheduleplanner.core.login.LoginBusinessLogic;
import com.scheduleplanner.core.login.dto.TokenOutDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/schedule-planner/account")
public class AuthenticationController {
    private final CreateAccountBusinessLogic createAccount;
    private final LoginBusinessLogic loginBusinessLogic;

    public AuthenticationController(@Autowired CreateAccountBusinessLogic createAccount,
                                    @Autowired LoginBusinessLogic loginBusinessLogic) {
        this.createAccount = createAccount;
        this.loginBusinessLogic = loginBusinessLogic;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createAccount(@RequestBody AccountInDto accountDto) {
        createAccount.runService(accountDto);
        return ResponseEntity.ok().body("Account created successfully");
    }

    @GetMapping("/login")
    public ResponseEntity<TokenOutDto> loginAccount(@RequestBody com.scheduleplanner.core.login.dto.AccountInDto accountDto) {
        var token = loginBusinessLogic.runService(accountDto);
        return ResponseEntity.ok().body(token);
    }
}
