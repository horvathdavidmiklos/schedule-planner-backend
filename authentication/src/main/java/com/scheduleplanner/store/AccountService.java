package com.scheduleplanner.store;

import java.util.Optional;

public interface AccountService {

    Optional<Account> findByEmail(String email);

    Optional<Account> findByUsername(String username);

    void save(Account account);

    void verifyAccount(String username);
}
