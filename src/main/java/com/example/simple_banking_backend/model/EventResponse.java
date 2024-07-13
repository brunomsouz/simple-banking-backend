package com.example.simple_banking_backend.model;

import com.example.simple_banking_backend.domain.Account;
import com.fasterxml.jackson.annotation.JsonInclude;

public record EventResponse(
        @JsonInclude(JsonInclude.Include.NON_NULL) Account origin,
        @JsonInclude(JsonInclude.Include.NON_NULL) Account destination
) {

}
