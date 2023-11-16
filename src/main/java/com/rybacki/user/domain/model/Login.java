package com.rybacki.user.domain.model;

public record Login(String value) {

    public Login {
        if (value.isBlank()) {
            throw new IllegalArgumentException("Login cannot be blank");
        }
    }
}
