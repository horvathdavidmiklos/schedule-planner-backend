package com.scheduleplanner.authorization.createaccount;

import com.scheduleplanner.authorization.secret.Encrypt;
import com.scheduleplanner.authorization.createaccount.dto.AccountInDto;
import com.scheduleplanner.authorization.createaccount.exception.*;
import com.scheduleplanner.common.VoidBusinessLogic;
import com.scheduleplanner.authorization.createaccount.entity.NewAccount;
import com.scheduleplanner.common.log.LogMethod;
import com.scheduleplanner.common.log.SensitiveData;
import com.scheduleplanner.dataaccesslayer.operations.authorization.AccountHandler;

import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import java.time.LocalDateTime;

public class CreateAccountBusinessLogic extends VoidBusinessLogic {
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final String USERNAME_REGEX = "^[a-zA-Z0-9.]+$";
    private final AccountHandler accountHandler;

    public CreateAccountBusinessLogic(AccountHandler accountHandler) {
        this.accountHandler = accountHandler;
    }

    @LogMethod
    public void runLogic(@SensitiveData AccountInDto dto) {
        execute(() -> createAccount(dto));
    }

    private void createAccount(AccountInDto dto) {
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
        try {
            return Encrypt.generateSHA256Hash(password);
        } catch (NoSuchAlgorithmException e) {
            throw new EncyrptException(e);
        }
    }

    private void passwordMatch(String password, String confirmPassword){
        if(!password.equals(confirmPassword)){
            throw new PasswordNotMatchingException();
        }
    }
}
