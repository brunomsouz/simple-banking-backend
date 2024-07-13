package com.example.simple_banking_backend.domain;

import com.example.simple_banking_backend.exception.BadRequestException;

public class Account {

    private final String id;
    private float balance;

    public Account(String id) {
        this.id = id;
        this.balance = 0;
    }

    public String getId() {
        return id;
    }

    public float getBalance() {
        return balance;
    }

    public void deposit(float amount) {
        if (amount < 0) {
            throw new BadRequestException("Amount must be positive");
        }

        this.balance += amount;
    }

    public void withdraw(float amount) {
        if (amount < 0) {
            throw new BadRequestException("Amount must be positive");
        }

        if (this.balance < amount) {
            throw new BadRequestException("Insufficient balance");
        }

        this.balance -= amount;
    }

}
