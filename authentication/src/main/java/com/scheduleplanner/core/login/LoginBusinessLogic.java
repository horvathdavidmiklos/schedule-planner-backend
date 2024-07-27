package com.scheduleplanner.core.login;
import com.scheduleplanner.core.login.dto.AccountInDto;
import com.scheduleplanner.core.login.dto.TokenOutDto;
import com.scheduleplanner.gateway.store.AccountHandler;
import com.scheduleplanner.secret.Encrypt;
import com.scheduleplanner.common.exception.baseexception.handled.WrongDataException;
import com.scheduleplanner.secret.TokenService;
import com.scheduleplanner.common.entity.Account;
import com.scheduleplanner.common.log.LogMethod;
import com.scheduleplanner.common.log.SensitiveData;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginBusinessLogic {
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final long EXPIRATION_TIME_IN_MILLIS = 1000L * 60 * 60 * 24; // 1 day

    private final AccountHandler accountHandler;
    private final Encrypt encrypt;
    private final TokenService tokenService;
    public LoginBusinessLogic(AccountHandler accountHandler, Encrypt encrypt, TokenService tokenService) {
        this.accountHandler = accountHandler;
        this.encrypt = encrypt;
        this.tokenService = tokenService;
    }

    @LogMethod
    public TokenOutDto runService(@SensitiveData AccountInDto dto) {
        var account = findAccount(dto);
        checkAccountExists(account);
        checkPassword(dto.password(), account.passwordHash());
        return new TokenOutDto(generateToken(account.username()));
    }

    private String generateToken(String username){
        return tokenService.generateToken(username,EXPIRATION_TIME_IN_MILLIS);
    }

    private void checkPassword(String password, String storedHashedPassword) {
        if(!encrypt.checkPassword(password, storedHashedPassword)) {
            throw new WrongDataException();
        }
    }

    private Account findAccount(AccountInDto dto) {
        return  isEmail(dto) ?
                accountHandler.findByEmail(dto.id()) :
                accountHandler.findByUsername(dto.id());
    }

    private void checkAccountExists(Account account){
        if(account == null) {
            throw new WrongDataException();
        }
    }

    private boolean  isEmail(AccountInDto dto) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(dto.id());
        return matcher.matches();
    }
}
