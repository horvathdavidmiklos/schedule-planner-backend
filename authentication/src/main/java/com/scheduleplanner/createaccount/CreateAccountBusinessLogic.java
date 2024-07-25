package com.scheduleplanner.createaccount;


import com.scheduleplanner.createaccount.dto.AccountInDto;
import com.scheduleplanner.secret.Encrypt;
import com.sheduleplanner.common.entity.NewAccount;
import com.sheduleplanner.common.exception.baseexception.handled.NotSupportedFormatException;
import com.sheduleplanner.common.exception.baseexception.handled.PasswordNotMatchingException;
import com.sheduleplanner.common.exception.baseexception.handled.ValueNotUniqueException;
import com.scheduleplanner.dataaccesslayer.operations.authorization.AccountHandler;
import com.sheduleplanner.common.log.LogMethod;
import com.sheduleplanner.common.log.SensitiveData;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import java.time.LocalDateTime;

public class CreateAccountBusinessLogic {
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final String USERNAME_REGEX = "^[a-zA-Z0-9.]+$";
    private final AccountHandler accountHandler;

    public CreateAccountBusinessLogic(AccountHandler accountHandler) {
        this.accountHandler = accountHandler;
    }

    @LogMethod
    public void runLogic(@SensitiveData AccountInDto dto) {
        checkRegex(dto);
        checkUnique(dto);
        passwordMatch(dto.password(),dto.passwordConfirmation());
        var account = new NewAccount()
                .email(dto.email())
                .passwordHash(passwordEncrypt(dto.password()))
                .username(dto.username())
                .createdAt(LocalDateTime.now());
        accountHandler.save(account);
    }

    private void checkUnique(AccountInDto dto) {
        if (!accountHandler.isUniqueEmail(dto.email())) {
            throw new ValueNotUniqueException("EMAIL");
        }
        if (!accountHandler.isUniqueUsername(dto.username())) {
            throw new ValueNotUniqueException("USERNAME");
        }
    }

    private void checkRegex(AccountInDto dto) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(dto.email());
        if (!matcher.matches()) {
            throw new NotSupportedFormatException("EMAIL"); //TODO validation with email,
        }
        pattern = Pattern.compile(USERNAME_REGEX);
        matcher = pattern.matcher(dto.username());
        if (!matcher.matches()) {
            throw new NotSupportedFormatException("USERNAME");
        }

    }

    private String passwordEncrypt(String password){
        return Encrypt.hashPassword(password);
    }

    private void passwordMatch(String password, String confirmPassword){
        if(!password.equals(confirmPassword)){
            throw new PasswordNotMatchingException();
        }
    }
}
