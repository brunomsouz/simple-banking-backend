package com.example.simple_banking_backend.repository;

import com.example.simple_banking_backend.domain.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AccountRepositoryImplTest {

    private AccountRepositoryImpl accountRepository;

    @BeforeEach
    void setup() {
        this.accountRepository = new AccountRepositoryImpl();
    }

    @Test
    void shouldSaveAccount() {
        Account account = new Account("123");
        accountRepository.save(account);
        assertTrue(accountRepository.findById("123").isPresent());
    }

    @Test
    void shouldReturnEmptyOptionalWhenAccountNotFound() {
        assertTrue(accountRepository.findById("unknown").isEmpty());
    }

    @Test
    void shouldResetAccounts() {
        Account account = new Account("123");
        accountRepository.save(account);
        accountRepository.reset();
        assertTrue(accountRepository.findById("123").isEmpty());
    }

    @Test
    void shouldRetrieveAcocunt() {
        Account account = new Account("123");
        accountRepository.save(account);
        Optional<Account> retrievedAccount = accountRepository.findById("123");
        assertTrue(retrievedAccount.isPresent());
        assertEquals("123", retrievedAccount.get().getId());
    }

    @Test
    void shouldRetrieveMultipleAccounts() {
        Account account1 = new Account("123");
        Account account2 = new Account("456");
        accountRepository.save(account1);
        accountRepository.save(account2);
        assertTrue(accountRepository.findById("123").isPresent());
        assertTrue(accountRepository.findById("456").isPresent());
    }
}