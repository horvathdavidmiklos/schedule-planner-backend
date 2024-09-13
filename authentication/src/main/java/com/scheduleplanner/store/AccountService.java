package com.scheduleplanner.store;

import com.scheduleplanner.common.exception.baseexception.unhandled.UnknownException;

import java.util.Optional;

public interface AccountService {

    Optional<Account> findByEmail(String email);

    Optional<Account> findByUsername(String username);

    void save(Account account);

    void verifyAccount(String username);
}
