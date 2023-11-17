package com.rybacki.user.domain.model;

public record Login(String value) {

    public Login {
        if (value.isBlank()) {
            throw new IllegalArgumentException("Login cannot be blank");
        }
        if (value.length() > 50) {
            throw new IllegalArgumentException("Login is too long");
        }
    }
}
