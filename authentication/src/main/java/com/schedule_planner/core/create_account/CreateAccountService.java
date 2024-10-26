package com.schedule_planner.core.create_account;


import com.schedule_planner.encrypt.TokenPurpose;
import com.schedule_planner.exception.baseexception.handled.EmptyFieldException;
import com.schedule_planner.exception.baseexception.handled.NotSupportedFormatException;
import com.schedule_planner.exception.baseexception.handled.PasswordNotMatchingException;
import com.schedule_planner.exception.baseexception.handled.ValueNotUniqueException;
import com.schedule_planner.log.SensitiveData;
import com.schedule_planner.core.create_account.dto.CreateAccountInDto;
import com.schedule_planner.encrypt.Encrypt;
import com.schedule_planner.encrypt.TokenService;
import com.schedule_planner.store.Account;
import com.schedule_planner.store.AccountService;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Objects.isNull;
import static org.apache.logging.log4j.util.Strings.isEmpty;

public class CreateAccountService {
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final String USERNAME_REGEX = "^[a-zA-Z0-9._]+$";
    private static final long EXPIRATION_TIME_IN_MILLIS = 1000L * 60 * 60 * 24; // 1 day

    private final AccountService accountService;
    private final Encrypt encrypt;
    private final SendVerificationEmail sendVerificationEmail;
    private final TokenService tokenService;

    public CreateAccountService(AccountService accountService,
                                Encrypt encrypt,
                                SendVerificationEmail sendVerificationEmail,
                                TokenService tokenService) {
        this.tokenService = tokenService;
        this.accountService = accountService;
        this.encrypt = encrypt;
        this.sendVerificationEmail = sendVerificationEmail;
    }


    public void runService(@SensitiveData CreateAccountInDto accountDto) {
        checkNonNull(accountDto);
        checkRegex(accountDto);
        checkUnique(accountDto);
        passwordMatch(accountDto.password(), accountDto.passwordConfirmation());
        var account = new Account()
                .email(accountDto.email())
                .passwordHash(passwordEncrypt(accountDto.password()))
                .username(accountDto.username().toLowerCase())
                .createdAt(LocalDateTime.now())
                .isVerified(false)
                .nickname(accountDto.nickname());
        var token = createToken(accountDto.username());
        sendVerificationEmail.send(accountDto.username(), accountDto.email(), token);
        accountService.save(account);
    }

    private String createToken(String username) {
        var token = tokenService.generateToken(username, EXPIRATION_TIME_IN_MILLIS, TokenPurpose.EMAIL_VERIFICATION.getName());
        return URLEncoder.encode(token, StandardCharsets.UTF_8);
    }

    private void checkNonNull(CreateAccountInDto dto) {
        if (isNull(dto)
                || isEmpty(dto.email())
                || isEmpty(dto.username())
                || isEmpty(dto.password())
                || isEmpty(dto.passwordConfirmation())
                ||isEmpty(dto.nickname())) {
            throw new EmptyFieldException();
        }
    }

    private void checkUnique(CreateAccountInDto dto) {
        Optional<Account> hasAccountByEmail = accountService.findByEmail(dto.email());
        Optional<Account> hasAccountByUsername = accountService.findByUsername(dto.username());
        if (hasAccountByEmail.isPresent() && hasAccountByEmail.get().email().equals(dto.email()) && hasAccountByEmail.get().isVerified()) {
            throw new ValueNotUniqueException("EMAIL_IS_ALREADY_TAKEN");
        }
        if (hasAccountByUsername.isPresent()
                && hasAccountByEmail.isEmpty()) {
            throw new ValueNotUniqueException("USERNAME_IS_ALREADY_TAKEN");
        }
    }

    private void checkRegex(CreateAccountInDto dto) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(dto.email());
        if (!matcher.matches()) {
            throw new NotSupportedFormatException("EMAIL_WRONG_SYNTAX");
        }
        pattern = Pattern.compile(USERNAME_REGEX);
        matcher = pattern.matcher(dto.username());
        if (!matcher.matches()) {
            throw new NotSupportedFormatException("USERNAME_WRONG_SYNTAX");
        }
    }

    private String passwordEncrypt(String password) {
        return encrypt.hashPassword(password);
    }

    private void passwordMatch(String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            throw new PasswordNotMatchingException();
        }
    }
}