package com.schedule_planner.store;

import com.schedule_planner.exception.baseexception.unhandled.UnknownException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Optional<Account> findByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

    @Override
    public Optional<Account> findByUsername(String username) {
        return accountRepository.findByUsername(username);
    }

    @Override
    public void save(Account account){
        accountRepository.save(account);
    }

    @Override
    public void verifyAccount(String username){
        var account = findByUsername(username).orElseThrow(()-> new UnknownException("ACCOUNT_NOT_FOUND"));
        account.isVerified(true);
        accountRepository.save(account);
    }

    @Override
    public void updateAccountByUsername(String username, Account updatedAccount) {
        accountRepository.findByUsername(username).ifPresent(existingAccount -> {
            accountRepository.delete(existingAccount);
            accountRepository.save(updatedAccount);
        });
    }

}