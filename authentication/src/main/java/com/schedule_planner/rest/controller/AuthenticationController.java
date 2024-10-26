package com.schedule_planner.rest.controller;

import com.schedule_planner.exception.handler.BaseController;
import com.schedule_planner.log.LogMethod;
import com.schedule_planner.log.SensitiveData;
import com.schedule_planner.core.create_account.CreateAccountService;
import com.schedule_planner.core.create_account.dto.CreateAccountInDto;
import com.schedule_planner.core.login.LoginService;
import com.schedule_planner.core.login.dto.LoginAccountInDto;
import com.schedule_planner.core.verified_account.VerifyAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/schedule-planner/account")
public class AuthenticationController extends BaseController {
    private final CreateAccountService createAccount;
    private final LoginService loginBusinessLogic;
    private final VerifyAccountService verifyAccount;

    public AuthenticationController(CreateAccountService createAccount,
                                    LoginService loginBusinessLogic,
                                    VerifyAccountService verifyAccount) {
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
    @PostMapping("/login")
    public ResponseEntity<?> loginAccount(@RequestBody @SensitiveData LoginAccountInDto accountDto) {


        return handledException(()->loginBusinessLogic.runService(accountDto));
    }


    @LogMethod
    @GetMapping("/verify-email")
    public ResponseEntity<String> verifyEmail(@RequestParam String token,
                                              @RequestParam String username,
                                              @RequestParam String purpose) {
        return handledException(()->verifyAccount.runService(token,username,purpose),
                ResponseEntity.ok().body("Email verification successful"));
    }

}


