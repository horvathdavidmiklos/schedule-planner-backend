package com.scheduleplanner.gateway.store.repository;

import com.scheduleplanner.gateway.store.entity.Account;
import com.scheduleplanner.gateway.store.entity.UnverifiedAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UnverifiedAccountRepository extends JpaRepository<UnverifiedAccount, String> {

    Optional<UnverifiedAccount> findByUsername(String username);

    Optional<UnverifiedAccount> findByEmail(String email);
}
