package com.urbanfeet_backend.Config.Auth;

public class ForbiddenRefreshException extends RuntimeException {
    public ForbiddenRefreshException(String message) {
        super(message);
    }
}