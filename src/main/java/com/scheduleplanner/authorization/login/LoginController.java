package com.scheduleplanner.authorization.login;

import com.scheduleplanner.authorization.login.dto.AccountInDto;
import com.scheduleplanner.authorization.login.dto.TokenOutDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/schedule-planner/account")
public class LoginController {
    private final LoginBusinessLogic loginBusinessLogic;

    public LoginController(@Autowired LoginBusinessLogic loginBusinessLogic) {
        this.loginBusinessLogic = loginBusinessLogic;
    }

    @GetMapping("/login")
    public ResponseEntity<TokenOutDto> createAccount(@RequestBody AccountInDto accountDto) {
        var token = loginBusinessLogic.runLogic(accountDto);
        return ResponseEntity.ok().body(token);
    }
}
