package com.example.simple_banking_backend.service;

import com.example.simple_banking_backend.domain.Account;
import com.example.simple_banking_backend.exception.BadRequestException;
import com.example.simple_banking_backend.exception.NotFoundException;
import com.example.simple_banking_backend.model.EventRequest;
import com.example.simple_banking_backend.model.EventResponse;
import com.example.simple_banking_backend.model.EventType;
import com.example.simple_banking_backend.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class EventServiceTest {

    @Mock
    private AccountRepository accountRepository;

    private EventService eventService;

    @BeforeEach
    void setup() {
        this.eventService = new EventService(this.accountRepository);
    }

    @Test
    void shouldGetBalance() {
        Account account = new Account("123");
        account.deposit(100);
        when(accountRepository.findById("123")).thenReturn(Optional.of(account));

        float balance = eventService.getBalance("123");

        assertEquals(100, balance);
    }

    @Test
    void shouldThrowNotFoundExceptionWhenGettingBalance() {
        when(accountRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> eventService.getBalance("123"));
    }

    @Test
    void shouldCreateAccountAndAddFunds() {
        EventRequest eventRequest = new EventRequest(EventType.deposit, null, "123", 50f);
        when(accountRepository.findById("123")).thenReturn(Optional.empty());

        EventResponse response = eventService.processEvent(eventRequest);

        assertNotNull(response.destination());
        assertEquals(50, response.destination().getBalance());
    }

    @Test
    void shouldDecreaseFundsWhenWithdraw() {
        Account account = new Account("123");
        account.deposit(50);
        when(accountRepository.findById("123")).thenReturn(Optional.of(account));

        EventRequest eventRequest = new EventRequest(EventType.withdraw, "123", null, 30f);
        EventResponse response = eventService.processEvent(eventRequest);

        assertEquals(20, response.origin().getBalance());
        assertEquals(account, response.origin());
    }

    @Test
    void shouldThrowBadRequestExceptionWhenWithdrawingMoreThanBalance() {
        Account account = new Account("123");
        account.deposit(25);
        when(accountRepository.findById("123")).thenReturn(Optional.of(account));

        EventRequest eventRequest = new EventRequest(EventType.withdraw, "123", null, 50f);

        assertThrows(BadRequestException.class, () -> eventService.processEvent(eventRequest));
    }

    @Test
    void shouldTransferAmmount() {
        Account origin = new Account("100");
        origin.deposit(100);
        Account destination = new Account("200");
        destination.deposit(50);
        when(accountRepository.findById("100")).thenReturn(Optional.of(origin));
        when(accountRepository.findById("200")).thenReturn(Optional.of(destination));

        EventRequest eventRequest = new EventRequest(EventType.transfer, "100", "200", 25.0f);
        EventResponse response = eventService.processEvent(eventRequest);

        assertEquals(origin, response.origin());
        assertEquals(destination, response.destination());
    }

    @Test
    void processEventWithInvalidTypeShouldThrowBadRequestException() {
        EventRequest eventRequest = new EventRequest(null, null, null, 0.0f);

        assertThrows(BadRequestException.class, () -> eventService.processEvent(eventRequest));
    }

}