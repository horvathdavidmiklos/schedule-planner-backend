package com.schedule_planner.core.login;
import com.schedule_planner.util.secret.Encrypt;
import com.schedule_planner.util.security.token.TokenExpirationTime;
import com.schedule_planner.util.security.token.TokenPurpose;
import com.schedule_planner.exception.baseexception.handled.UnverifiedAccountException;
import com.schedule_planner.core.login.dto.LoginAccountInDto;
import com.schedule_planner.core.login.dto.TokenOutDto;
import com.schedule_planner.store.Account;
import com.schedule_planner.store.AccountService;
import com.schedule_planner.exception.baseexception.handled.WrongDataException;
import com.schedule_planner.log.SensitiveData;
import com.schedule_planner.util.security.token.TokenService;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginService {
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    private final AccountService accountService;
    private final Encrypt encrypt;
    private final TokenService tokenService;
    public LoginService(AccountService accountService, Encrypt encrypt, TokenService tokenService) {
        this.accountService=  accountService;
        this.encrypt = encrypt;
        this.tokenService = tokenService;
    }

    public TokenOutDto runService(@SensitiveData LoginAccountInDto dto) {
        var account = findAccount(dto).orElseThrow(WrongDataException::new);
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
        return tokenService.generateToken(username, TokenExpirationTime.ONE_YEAR, TokenPurpose.LOGIN);
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
