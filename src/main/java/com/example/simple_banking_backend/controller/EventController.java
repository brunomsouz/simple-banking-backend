package com.example.simple_banking_backend.controller;

import com.example.simple_banking_backend.model.EventRequest;
import com.example.simple_banking_backend.model.EventResponse;
import com.example.simple_banking_backend.service.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping("/reset")
    public String reset() {
        this.eventService.reset();
        return "OK";
    }

    @PostMapping("/event")
    @ResponseStatus(HttpStatus.CREATED)
    public EventResponse processEvent(@RequestBody EventRequest eventRequest) {
        return this.eventService.processEvent(eventRequest);
    }

    @GetMapping("/balance")
    public float getBalance(@RequestParam("account_id") String accountId) {
        return this.eventService.getBalance(accountId);
    }

}
