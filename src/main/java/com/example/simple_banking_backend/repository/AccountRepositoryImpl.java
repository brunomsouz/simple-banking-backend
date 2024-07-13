package com.example.simple_banking_backend.repository;

import com.example.simple_banking_backend.domain.Account;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class AccountRepositoryImpl implements AccountRepository {

    private final Map<String, Account> accounts;

    public AccountRepositoryImpl() {
        this.accounts = new HashMap<>();
    }

    @Override
    public void reset() {
        this.accounts.clear();
    }

    @Override
    public void save(Account account) {
        this.accounts.put(account.getId(), account);
    }

    @Override
    public Optional<Account> findById(String id) {
        return Optional.ofNullable(this.accounts.get(id));
    }

}
