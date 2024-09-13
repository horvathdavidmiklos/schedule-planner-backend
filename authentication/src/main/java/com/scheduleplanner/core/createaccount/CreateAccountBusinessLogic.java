package com.scheduleplanner.core.createaccount;


import com.scheduleplanner.core.createaccount.dto.AccountInDto;
import com.scheduleplanner.gateway.store.AccountRepository;
import com.scheduleplanner.secret.Encrypt;
import com.scheduleplanner.common.exception.baseexception.handled.EmptyFieldException;
import com.scheduleplanner.common.exception.baseexception.handled.NotSupportedFormatException;
import com.scheduleplanner.common.exception.baseexception.handled.PasswordNotMatchingException;
import com.scheduleplanner.common.exception.baseexception.handled.ValueNotUniqueException;
import com.scheduleplanner.common.log.SensitiveData;
import com.scheduleplanner.secret.TokenService;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.time.LocalDateTime;
import static java.util.Objects.isNull;

public class CreateAccountBusinessLogic {
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final String USERNAME_REGEX = "^[a-zA-Z0-9._]+$";
    private static final long EXPIRATION_TIME_IN_MILLIS = 1000L * 60 * 60 * 24; // 1 day

    private final AccountRepository repositoryHandler;
    private final Encrypt encrypt;
    private final SendVerificationEmail sendVerificationEmail;
    private final TokenService tokenService;

    public CreateAccountBusinessLogic(AccountRepository repositoryHandler, Encrypt encrypt, SendVerificationEmail sendVerificationEmail,
                                      TokenService tokenService) {
        this.tokenService = tokenService;
        this.repositoryHandler = repositoryHandler;
        this.encrypt = encrypt;
        this.sendVerificationEmail = sendVerificationEmail;
    }

    public void runService(@SensitiveData AccountInDto accountDto) {
        checkNonNull(accountDto);
        checkRegex(accountDto);
        checkUnique(accountDto);
        passwordMatch(accountDto.password(),accountDto.passwordConfirmation());
        var token = verify_token(accountDto.username());
        var account = new NewAccount()
                .email(accountDto.email())
                .passwordHash(passwordEncrypt(accountDto.password()))
                .username(accountDto.username().toUpperCase())
                .createdAt(LocalDateTime.now())
                .token(token);
        sendVerificationEmail.send(accountDto.username(),accountDto.email(),token);
        repositoryHandler.saveUnverified(account);
    }

    private String verify_token(String username){
        return tokenService.generateToken(username,EXPIRATION_TIME_IN_MILLIS);
    }

    private void checkNonNull(AccountInDto dto) {
        if(isNull(dto) || isNull(dto.email()) || isNull(dto.username()) || isNull(dto.password()) || isNull(dto.passwordConfirmation())){
            throw new EmptyFieldException();
        }
    }

    private void checkUnique(AccountInDto dto) {
        if (!repositoryHandler.isUniqueEmail(dto.email())) {
            throw new ValueNotUniqueException("EMAIL");
        }
        if (!repositoryHandler.isUniqueUsername(dto.username().toUpperCase())) {
            throw new ValueNotUniqueException("USERNAME");
        }
    }

    private void checkRegex(AccountInDto dto) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(dto.email());
        if (!matcher.matches()) {
            throw new NotSupportedFormatException("EMAIL");
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
