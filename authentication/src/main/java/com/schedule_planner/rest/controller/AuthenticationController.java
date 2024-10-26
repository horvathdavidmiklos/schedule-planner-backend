package com.schedule_planner.rest.controller;

import com.schedule_planner.core.login.LoginThirdPartyApplicationService;
import com.schedule_planner.core.login.dto.ThirdPartyApplicationDataDto;
import com.schedule_planner.exception.handler.BaseController;
import com.schedule_planner.log.LogMethod;
import com.schedule_planner.log.SensitiveData;
import com.schedule_planner.core.create_account.CreateAccountService;
import com.schedule_planner.core.create_account.dto.CreateAccountInDto;
import com.schedule_planner.core.login.LoginService;
import com.schedule_planner.core.login.dto.LoginAccountInDto;
import com.schedule_planner.core.verified_account.VerifyAccountService;
import com.schedule_planner.util.security.authorization.Authorized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("app/v1/schedule-planner/account")
public class AuthenticationController extends BaseController {
    private final CreateAccountService createAccount;
    private final LoginService loginBusinessLogic;
    private final VerifyAccountService verifyAccount;
    private final LoginThirdPartyApplicationService loginThirdPartyApplicationService;

    public AuthenticationController(CreateAccountService createAccount,
                                    LoginService loginBusinessLogic,
                                    VerifyAccountService verifyAccount,
                                    LoginThirdPartyApplicationService loginThirdPartyApplicationService) {
        this.createAccount = createAccount;
        this.loginBusinessLogic = loginBusinessLogic;
        this.verifyAccount = verifyAccount;
        this.loginThirdPartyApplicationService = loginThirdPartyApplicationService;
    }

    @LogMethod
    @PostMapping("/create")
    public ResponseEntity<String> createAccount(@RequestBody  CreateAccountInDto accountDto) {
        return handledException(()->createAccount.runService(accountDto),ResponseEntity.ok("Account created successfully"));
    }

    @LogMethod
    @PostMapping("/login")
    public ResponseEntity<?> loginAccount(@RequestBody LoginAccountInDto accountDto) {


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

    @LogMethod
    @PostMapping("/third-party-login")
    public ResponseEntity<?> loginWithThirdPartyAccount(@RequestBody ThirdPartyApplicationDataDto thirdPartyApplicationDataDto) {
        return handledException(()->loginThirdPartyApplicationService.runService(thirdPartyApplicationDataDto));
    }

    @LogMethod
    @GetMapping("/secure-data")
    @Authorized
    public String getSecureData(String username) {
        return "Ez egy biztonságos adat, amit a felhasználó " + username + " hívott meg.";
    }


}


