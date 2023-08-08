package com.example.wantedboard.exception.post;

public class NotFoundPostException extends RuntimeException {
    public NotFoundPostException(String message) {
        super(message);
    }
}
