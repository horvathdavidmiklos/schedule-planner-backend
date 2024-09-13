package com.scheduleplanner.gateway.store.service;

import com.scheduleplanner.gateway.store.entity.Account;
import com.scheduleplanner.gateway.store.entity.UnverifiedAccount;
import com.scheduleplanner.gateway.store.repository.AccountRepository;
import com.scheduleplanner.gateway.store.repository.UnverifiedAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Optional<Account> findByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

    public Optional<Account> findByUsername(String username) {
        return accountRepository.findByUsername(username);
    }

    public void save(Account account){
        accountRepository.save(account);
    }

}