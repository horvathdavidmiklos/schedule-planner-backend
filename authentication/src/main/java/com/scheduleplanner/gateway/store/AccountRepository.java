package com.scheduleplanner.gateway.store;

import com.scheduleplanner.gateway.store.entity.UnverifiedAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository  extends JpaRepository<UnverifiedAccount, String> {
    void saveUnverified(NewAccount account);

    boolean isUniqueUsername(String username);


    boolean isUniqueEmail(String email);

    Account findByEmail(String email);

    Account findByUsername(String username);
    void saveVerifiedAccount(String username);
}
