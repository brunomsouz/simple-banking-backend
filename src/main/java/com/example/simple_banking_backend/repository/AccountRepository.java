package com.example.simple_banking_backend.repository;

import com.example.simple_banking_backend.domain.Account;

import java.util.Optional;

public interface AccountRepository {

    void reset();
    void save(Account account);
    Optional<Account> findById(String id);

}
