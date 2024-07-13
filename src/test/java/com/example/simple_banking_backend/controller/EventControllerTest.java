package com.example.simple_banking_backend.controller;

import com.example.simple_banking_backend.model.EventRequest;
import com.example.simple_banking_backend.model.EventResponse;
import com.example.simple_banking_backend.service.EventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class EventControllerTest {

    @Mock
    private EventService eventService;

    private EventController eventController;

    @BeforeEach
    void setup() {
        this.eventController = new EventController(this.eventService);
    }

    @Test
    void resetShouldReturnOkStatus() {
        doNothing().when(this.eventService).reset();

        assertEquals("OK", this.eventController.reset());
    }

    @Test
    void processEventShouldReturnCreatedStatus() {
        EventRequest eventRequest = new EventRequest(null, null, null, null);
        EventResponse expectedResponse = new EventResponse(null, null);
        when(eventService.processEvent(any(EventRequest.class))).thenReturn(expectedResponse);

        EventResponse actualResponse = eventController.processEvent(eventRequest);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void getBalanceShouldReturnCorrectAmount() {
        String accountId = "123";
        float expectedBalance = 100.0f;
        when(eventService.getBalance(accountId)).thenReturn(expectedBalance);

        float actualBalance = eventController.getBalance(accountId);

        assertEquals(expectedBalance, actualBalance);
    }

    @Test
    void getBalanceWithInvalidAccountIdShouldReturnZero() {
        String invalidAccountId = "invalid";
        float expectedBalance = 0.0f;
        when(eventService.getBalance(invalidAccountId)).thenReturn(expectedBalance);

        float actualBalance = eventController.getBalance(invalidAccountId);

        assertEquals(expectedBalance, actualBalance);
    }
}