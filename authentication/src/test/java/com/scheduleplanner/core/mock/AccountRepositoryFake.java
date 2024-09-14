package com.scheduleplanner.core.mock;

import com.scheduleplanner.store.Account;
import com.scheduleplanner.store.AccountRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class AccountRepositoryFake implements AccountRepository {
    public Optional<Account> findByUsernameResult;
    public Optional<Account> findByEmailResult;
    public Account inputAccount;
    public boolean saveIsCalled;

    public AccountRepositoryFake() {
        saveIsCalled = false;
    }

    @Override
    public Optional<Account> findByUsername(String username) {
        return findByUsernameResult;
    }

    @Override
    public Optional<Account> findByEmail(String email) {
        return findByEmailResult;
    }


    @Override
    public void flush() {

    }

    @Override
    public <S extends Account> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Account> List<S> saveAllAndFlush(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public void deleteAllInBatch(Iterable<Account> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<String> strings) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Account getOne(String s) {
        return null;
    }

    @Override
    public Account getById(String s) {
        return null;
    }

    @Override
    public Account getReferenceById(String s) {
        return null;
    }

    @Override
    public <S extends Account> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Account> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends Account> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends Account> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Account> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Account> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Account, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends Account> S save(S entity) {
        saveIsCalled = true;
        inputAccount= entity;
        return null;
    }

    @Override
    public <S extends Account> List<S> saveAll(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public Optional<Account> findById(String s) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public List<Account> findAll() {
        return List.of();
    }

    @Override
    public List<Account> findAllById(Iterable<String> strings) {
        return List.of();
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(String s) {

    }

    @Override
    public void delete(Account entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends String> strings) {

    }

    @Override
    public void deleteAll(Iterable<? extends Account> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Account> findAll(Sort sort) {
        return List.of();
    }

    @Override
    public Page<Account> findAll(Pageable pageable) {
        return null;
    }
}
