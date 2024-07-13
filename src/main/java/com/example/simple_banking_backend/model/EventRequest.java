package com.example.simple_banking_backend.model;

public record EventRequest(EventType type, String origin, String destination, Float amount) {

}