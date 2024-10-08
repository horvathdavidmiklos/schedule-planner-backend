package com.scheduleplanner.rest.controller;

import com.scheduleplanner.common.exception.handler.BaseController;
import com.scheduleplanner.common.log.LogMethod;
import com.scheduleplanner.common.log.SensitiveData;
import com.scheduleplanner.core.createaccount.CreateAccountBusinessLogic;
import com.scheduleplanner.core.createaccount.dto.CreateAccountInDto;
import com.scheduleplanner.core.login.LoginBusinessLogic;
import com.scheduleplanner.core.login.dto.LoginAccountInDto;
import com.scheduleplanner.core.verifiedaccount.VerifyAccountBusinessLogic;
import com.sun.mail.iap.Response;
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
    public ResponseEntity<String> createAccount(@RequestBody @SensitiveData CreateAccountInDto accountDto) {
        return handledException(()->createAccount.runService(accountDto),ResponseEntity.ok("Account created successfully"));
    }

    @LogMethod
    @GetMapping("/login")
    public ResponseEntity<?> loginAccount(@RequestBody @SensitiveData LoginAccountInDto accountDto) {
        return handledException(()->loginBusinessLogic.runService(accountDto));
    }


    @LogMethod
    @GetMapping("/verify-email/{token}/{username}")
    public ResponseEntity<String> verifyEmail(@PathVariable String token, @PathVariable String username) {
        return handledException(()->verifyAccount.runService(token,username), ResponseEntity.ok().body("Email verification successful"));
    }

}
