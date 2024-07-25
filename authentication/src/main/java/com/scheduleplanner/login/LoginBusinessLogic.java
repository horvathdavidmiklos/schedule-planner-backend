package com.scheduleplanner.login;
import com.scheduleplanner.dataaccesslayer.operations.authorization.AccountHandler;
import com.scheduleplanner.login.dto.AccountInDto;
import com.scheduleplanner.login.dto.TokenOutDto;
import com.scheduleplanner.secret.Encrypt;
import com.sheduleplanner.common.exception.baseexception.handled.WrongDataException;
import com.scheduleplanner.secret.TokenService;
import com.sheduleplanner.common.entity.Account;
import com.sheduleplanner.common.log.LogMethod;
import com.sheduleplanner.common.log.SensitiveData;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginBusinessLogic {
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    private final AccountHandler accountHandler;
    public LoginBusinessLogic(AccountHandler accountHandler) {
        this.accountHandler = accountHandler;
    }

    @LogMethod
    public TokenOutDto runLogic(@SensitiveData AccountInDto dto) {
        var account = findAccount(dto);
        checkAccountExists(account);
        checkPassword(dto.password(), account.passwordHash());
        return new TokenOutDto(generateToken(account.username()));
    }

    private String generateToken(String username){
        return TokenService.generateToken(username);
    }

    private void checkPassword(String password, String storedHashedPassword) {
        if(!Encrypt.checkPassword(password, storedHashedPassword)) {
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
