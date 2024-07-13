package com.example.simple_banking_backend.domain;

import com.example.simple_banking_backend.exception.BadRequestException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AccountTest {

    @Test
    public void shouldIncreaseBalanceWhenDeposit() {
        Account account = new Account("123");
        account.deposit(100);
        assertEquals(100, account.getBalance());
    }

    @Test
    public void shouldDecreaseBalanceWhenWithdraw() {
        Account account = new Account("123");
        account.deposit(200);
        account.withdraw(100);
        assertEquals(100, account.getBalance());
    }

    @Test
    public void shouldDecreaseToZeroWhenWithdrawingTotalBalance() {
        Account account = new Account("123");
        account.deposit(100);
        account.withdraw(100);
        assertEquals(0, account.getBalance());
    }

    @Test
    public void shouldThrowBadRequestExceptionWhenBalanceNotSufficient() {
        Account account = new Account("123");
        account.deposit(50);
        assertThrows(BadRequestException.class, () -> account.withdraw(100));
    }

    @Test
    public void shouldThrowBadRequestWhenDepositingAndAmountIsNegative() {
        Account account = new Account("123");
        assertThrows(BadRequestException.class, () -> account.deposit(-50));
        assertEquals(0, account.getBalance());
    }

    @Test
    public void shouldThrowBadRequestWhenWithdrawingAndAmountIsNegative() {
        Account account = new Account("123");
        assertThrows(BadRequestException.class, () -> account.withdraw(-50));
        assertEquals(0, account.getBalance());
    }
}