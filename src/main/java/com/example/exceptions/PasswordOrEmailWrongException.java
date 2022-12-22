package com.example.exceptions;

public class PasswordOrEmailWrongException extends RuntimeException{
    public PasswordOrEmailWrongException(String message) {
        super(message);
    }
}
