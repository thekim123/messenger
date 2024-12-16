package com.namusd.messenger.handler.ex;

public class BadRequestApiException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public BadRequestApiException(String message) {
        super(message);
    }
}
