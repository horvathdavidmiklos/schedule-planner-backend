package com.scheduleplanner.authorization.login;

import com.scheduleplanner.authorization.login.exception.WrongDataException;
import com.scheduleplanner.authorization.secret.Encrypt;
import com.scheduleplanner.authorization.secret.TokenService;
import com.scheduleplanner.authorization.createaccount.exception.EncyrptException;
import com.scheduleplanner.authorization.login.dto.AccountInDto;
import com.scheduleplanner.authorization.login.dto.TokenOutDto;
import com.scheduleplanner.common.ReturnedBusinessLogic;
import com.scheduleplanner.authorization.login.entity.Account;
import com.scheduleplanner.common.log.LogMethod;
import com.scheduleplanner.common.log.SensitiveData;
import com.scheduleplanner.dataaccesslayer.operations.authorization.AccountHandler;

import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginBusinessLogic extends ReturnedBusinessLogic<TokenOutDto> {
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    private final AccountHandler accountHandler;
    public LoginBusinessLogic(AccountHandler accountHandler) {
        this.accountHandler = accountHandler;
    }

    @LogMethod
    public TokenOutDto runLogic(@SensitiveData AccountInDto dto) {
        return execute(() -> login(dto));
    }

    private TokenOutDto login(AccountInDto dto) {
        var account = findAccount(dto);
       checkAccountExists(account);
       checkPassword(dto.password(), account.passwordHash());
       return new TokenOutDto(generateToken(account.username()));
    }

    private String generateToken(String username){
        return TokenService.generateToken(username);
    }

    private void checkPassword(String password, String storedHashedPassword) {
        if(!passwordEncrypt(password).equals(storedHashedPassword)) {
            throw new WrongDataException();
        }
    }

    private String passwordEncrypt(String password){
        try {
            return Encrypt.generateSHA256Hash(password);
        } catch (NoSuchAlgorithmException e) {
            throw new EncyrptException(e);
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
