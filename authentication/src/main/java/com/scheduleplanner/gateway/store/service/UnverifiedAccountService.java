package com.scheduleplanner.gateway.store.service;

import com.scheduleplanner.gateway.store.entity.Account;
import com.scheduleplanner.gateway.store.entity.UnverifiedAccount;
import com.scheduleplanner.gateway.store.repository.AccountRepository;
import com.scheduleplanner.gateway.store.repository.UnverifiedAccountRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UnverifiedAccountService {

    private final UnverifiedAccountRepository unverifiedRepository;

    public UnverifiedAccountService(UnverifiedAccountRepository unverifiedRepository) {
        this.unverifiedRepository = unverifiedRepository;
    }

    public void saveUnverifiedAccount(UnverifiedAccount unverifiedAccount) {
        unverifiedRepository.save(unverifiedAccount);
    }

    public Optional<UnverifiedAccount> findByEmail(String email) {
        return unverifiedRepository.findByEmail(email);
    }

    public Optional<UnverifiedAccount> findByUsername(String username) {
        return unverifiedRepository.findByUsername(username);
    }

    public void editUnverifiedAccount(UnverifiedAccount unverifiedAccount) {
        unverifiedRepository.findALl
    }

}