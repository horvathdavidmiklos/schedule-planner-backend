package com.scheduleplanner.core.createaccount;


import com.scheduleplanner.core.createaccount.dto.AccountInDto;
import com.scheduleplanner.gateway.store.AccountHandler;
import com.scheduleplanner.secret.Encrypt;
import com.scheduleplanner.common.entity.NewAccount;
import com.scheduleplanner.common.exception.baseexception.handled.EmptyFieldException;
import com.scheduleplanner.common.exception.baseexception.handled.NotSupportedFormatException;
import com.scheduleplanner.common.exception.baseexception.handled.PasswordNotMatchingException;
import com.scheduleplanner.common.exception.baseexception.handled.ValueNotUniqueException;
import com.scheduleplanner.common.log.LogMethod;
import com.scheduleplanner.common.log.SensitiveData;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.time.LocalDateTime;
import static java.util.Objects.isNull;

public class CreateAccountBusinessLogic {
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final String USERNAME_REGEX = "^[a-zA-Z0-9._]+$";

    private final AccountHandler accountHandler;
    private final Encrypt encrypt;

    public CreateAccountBusinessLogic(AccountHandler accountHandler, Encrypt encrypt) {
        this.accountHandler = accountHandler;
        this.encrypt = encrypt;
    }

    @LogMethod
    public void runService(@SensitiveData AccountInDto dto) {
        checkNonNull(dto);
        checkRegex(dto);
        checkUnique(dto);
        passwordMatch(dto.password(),dto.passwordConfirmation());
        var account = new NewAccount()
                .email(dto.email())
                .passwordHash(passwordEncrypt(dto.password()))
                .username(dto.username().toUpperCase())
                .createdAt(LocalDateTime.now());
        accountHandler.save(account);
    }

    private void checkNonNull(AccountInDto dto) {
        if(isNull(dto) || isNull(dto.email()) || isNull(dto.username()) || isNull(dto.password()) || isNull(dto.passwordConfirmation())){
            throw new EmptyFieldException();
        }
    }

    private void checkUnique(AccountInDto dto) {
        if (!accountHandler.isUniqueEmail(dto.email())) {
            throw new ValueNotUniqueException("EMAIL");
        }
        if (!accountHandler.isUniqueUsername(dto.username().toUpperCase())) {
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
        return encrypt.hashPassword(password);
    }

    private void passwordMatch(String password, String confirmPassword){
        if(!password.equals(confirmPassword)){
            throw new PasswordNotMatchingException();
        }
    }
}
