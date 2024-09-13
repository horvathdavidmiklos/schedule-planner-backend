package com.scheduleplanner.gateway.store.repository;

import com.scheduleplanner.gateway.store.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, String> {

    Optional<Account> findByUsername(String username);

    Optional<Account> findByEmail(String email);
    void save(Account account);
}
