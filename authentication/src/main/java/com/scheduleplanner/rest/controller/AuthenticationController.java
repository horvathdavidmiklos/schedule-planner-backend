package com.scheduleplanner.rest.controller;

import com.scheduleplanner.common.exception.handler.BaseController;
import com.scheduleplanner.common.log.LogMethod;
import com.scheduleplanner.common.log.SensitiveData;
import com.scheduleplanner.core.createaccount.CreateAccountBusinessLogic;
import com.scheduleplanner.core.createaccount.dto.AccountInDto;
import com.scheduleplanner.core.login.LoginBusinessLogic;
import com.scheduleplanner.core.verifiedaccount.VerifyAccountBusinessLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/schedule-planner/account")
public class AuthenticationController extends BaseController {
    private final CreateAccountBusinessLogic createAccount;
    private final LoginBusinessLogic loginBusinessLogic;
    private final VerifyAccountBusinessLogic verifyAccount;

    public AuthenticationController(@Autowired CreateAccountBusinessLogic createAccount,
                                    @Autowired LoginBusinessLogic loginBusinessLogic,
                                    @Autowired VerifyAccountBusinessLogic verifyAccount) {
        this.createAccount = createAccount;
        this.loginBusinessLogic = loginBusinessLogic;
        this.verifyAccount = verifyAccount;
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


    @LogMethod
    @GetMapping("/verify-email/{token}/{username}")
    public ResponseEntity<String> verifyEmail(@PathVariable String token, @PathVariable String username) {
        handledException(()->verifyAccount.runService(token,username),"Account created successfully");
        return ResponseEntity.ok("Email verification successful");
    }

}
