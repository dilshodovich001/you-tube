package com.example.exceptions;

public class ProfileCreateException extends RuntimeException{
    public ProfileCreateException(String message) {
        super(message);
    }
}
