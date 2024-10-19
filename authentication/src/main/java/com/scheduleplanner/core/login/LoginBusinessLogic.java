package com.scheduleplanner.core.login;
import com.scheduleplanner.exception.baseexception.handled.EmailAddressNotFound;
import com.scheduleplanner.exception.baseexception.handled.UnverifiedAccountException;
import com.scheduleplanner.core.login.dto.LoginAccountInDto;
import com.scheduleplanner.core.login.dto.TokenOutDto;
import com.scheduleplanner.store.Account;
import com.scheduleplanner.store.AccountService;
import com.scheduleplanner.encrypt.Encrypt;
import com.scheduleplanner.exception.baseexception.handled.WrongDataException;
import com.scheduleplanner.encrypt.TokenService;
import com.scheduleplanner.log.SensitiveData;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginBusinessLogic {
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final long EXPIRATION_TIME_IN_MILLIS = 1000L * 60 * 60 * 24; // 1 day

    private final AccountService accountService;
    private final Encrypt encrypt;
    private final TokenService tokenService;
    public LoginBusinessLogic(AccountService accountService, Encrypt encrypt, TokenService tokenService) {
        this.accountService=  accountService;
        this.encrypt = encrypt;
        this.tokenService = tokenService;
    }

    public TokenOutDto runService(@SensitiveData LoginAccountInDto dto) {
        var account = findAccount(dto).orElseThrow(()->new EmailAddressNotFound("EMAIL_ADDRESS_NOT_FOUND"));
        checkPassword(dto.password(), account.passwordHash());
        checkVerified(account.isVerified());
        return new TokenOutDto(generateToken(account.username()));
    }
    private void checkVerified(boolean isVerified) {
        if(!isVerified) {
            throw new UnverifiedAccountException("ACCOUNT_NOT_VERIFIED");
        }
    }

    private String generateToken(String username){
        return tokenService.generateToken(username,EXPIRATION_TIME_IN_MILLIS);
    }

    private void checkPassword(String password, String storedHashedPassword) {
        if(!encrypt.checkPassword(password, storedHashedPassword)) {
            throw new WrongDataException();
        }
    }

    private Optional<Account> findAccount(LoginAccountInDto dto) {
        return  isEmail(dto) ?
                accountService.findByEmail(dto.id()) :
                accountService.findByUsername(dto.id());
    }

    private boolean  isEmail(LoginAccountInDto dto) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(dto.id());
        return matcher.matches();
    }
}
