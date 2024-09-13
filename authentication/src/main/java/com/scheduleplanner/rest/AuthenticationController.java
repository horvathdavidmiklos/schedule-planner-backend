package com.scheduleplanner.rest;

import com.scheduleplanner.common.exception.baseexception.handled.HandledException;
import com.scheduleplanner.common.exception.handler.BaseController;
import com.scheduleplanner.common.log.LogMethod;
import com.scheduleplanner.common.log.SensitiveData;
import com.scheduleplanner.core.createaccount.CreateAccountBusinessLogic;
import com.scheduleplanner.core.createaccount.dto.AccountInDto;
import com.scheduleplanner.core.login.LoginBusinessLogic;
import com.scheduleplanner.core.login.dto.TokenOutDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.function.Supplier;

@RestController
@RequestMapping("/schedule-planner/account")
public class AuthenticationController extends BaseController {
    private final CreateAccountBusinessLogic createAccount;
    private final LoginBusinessLogic loginBusinessLogic;

    public AuthenticationController(@Autowired CreateAccountBusinessLogic createAccount,
                                    @Autowired LoginBusinessLogic loginBusinessLogic) {
        this.createAccount = createAccount;
        this.loginBusinessLogic = loginBusinessLogic;
    }

    @LogMethod
    @PostMapping("/create")
    public ResponseEntity<String> createAccount(@RequestBody @SensitiveData AccountInDto accountDto) {
        return handledException(()->createAccount.runService(accountDto),"Account created successfully");
    }

    @LogMethod
    @GetMapping("/login")
    public ResponseEntity<?> loginAccount(@RequestBody @SensitiveData com.scheduleplanner.core.login.dto.AccountInDto accountDto) {
        return handledException(()->loginBusinessLogic.runService(accountDto));
    }

    @GetMapping("/verify-email")
    public ResponseEntity<String> verifyEmail(@RequestParam("token") String token) {
        handledException(()->createAccount.runService(token),"Account created successfully")
        return ResponseEntity.ok("Email verification successful");
    }

}
