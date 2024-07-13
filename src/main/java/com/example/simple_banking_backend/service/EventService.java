package com.example.simple_banking_backend.service;

import com.example.simple_banking_backend.domain.Account;
import com.example.simple_banking_backend.exception.BadRequestException;
import com.example.simple_banking_backend.exception.NotFoundException;
import com.example.simple_banking_backend.model.EventRequest;
import com.example.simple_banking_backend.model.EventResponse;
import com.example.simple_banking_backend.repository.AccountRepository;
import org.springframework.stereotype.Service;

@Service
public class EventService {

    private final AccountRepository accountRepository;

    public EventService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void reset() {
        this.accountRepository.reset();
    }

    private Account findAccount(String accountId) {
        return this.accountRepository.findById(accountId)
                .orElseThrow(() -> new NotFoundException("Account not found"));
    }

    public float getBalance(String accountId) {
        return this.findAccount(accountId).getBalance();
    }

    public EventResponse processEvent(EventRequest eventRequest) {
        this.validateEvent(eventRequest);

        return switch (eventRequest.type()) {
            case deposit -> this.deposit(eventRequest);
            case withdraw -> this.withdraw(eventRequest);
            case transfer -> this.transfer(eventRequest);
        };
    }

    private EventResponse deposit(EventRequest eventRequest) {
        Account account = this.accountRepository.findById(eventRequest.destination())
                .orElseGet(() -> new Account(eventRequest.destination()));

        account.deposit(eventRequest.amount());
        this.accountRepository.save(account);

        return new EventResponse(null, account);
    }

    private EventResponse withdraw(EventRequest eventRequest) {
        Account account = this.findAccount(eventRequest.origin());

        account.withdraw(eventRequest.amount());

        return new EventResponse(account, null);
    }

    private EventResponse transfer(EventRequest eventRequest) {
        Account origin = this.findAccount(eventRequest.origin());
        Account destination = this.accountRepository.findById(eventRequest.destination())
                .orElseGet(() -> new Account(eventRequest.destination()));

        origin.withdraw(eventRequest.amount());
        destination.deposit(eventRequest.amount());

        this.accountRepository.save(origin);
        this.accountRepository.save(destination);

        return new EventResponse(origin, destination);
    }

    private void validateEvent(EventRequest eventRequest) {
        if (eventRequest.amount() == null || eventRequest.amount() <= 0) {
            throw new BadRequestException("A positive amount must be provided");
        }

        switch (eventRequest.type()) {
            case deposit -> {
                if (eventRequest.destination() == null) throw new BadRequestException("Destination account is required");
            }
            case withdraw -> {
                if (eventRequest.origin() == null) throw new BadRequestException("Origin account is required");
            }
            case transfer -> {
                if (eventRequest.origin() == null || eventRequest.destination() == null) {
                    throw new BadRequestException("Origin and destination accounts are required");
                }
            }
            default -> throw new BadRequestException("Event type is required");
        }
    }

}
