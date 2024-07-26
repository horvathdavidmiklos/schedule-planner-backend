package com.scheduleplanner.endpoint.createaccount;

import com.scheduleplanner.secret.AccountInDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/schedule-planner/account")
public class CreateAccountController {
    private final CreateAccountBusinessLogic createAccount;

    public CreateAccountController(@Autowired CreateAccountBusinessLogic createAccount) {
        this.createAccount = createAccount;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createAccount(@RequestBody AccountInDto accountDto) {
        createAccount.runLogic(accountDto);
        return ResponseEntity.ok().body("Account created successfully");
    }
}
