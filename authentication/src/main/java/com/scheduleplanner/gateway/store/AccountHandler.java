package com.scheduleplanner.gateway.store;

import com.scheduleplanner.common.entity.Account;
import com.scheduleplanner.common.entity.NewAccount;

public interface AccountHandler {
    void save(NewAccount account);

    boolean isUniqueUsername(String username);


    boolean isUniqueEmail(String email);

    Account findByEmail(String email);

    Account findByUsername(String username);
}
