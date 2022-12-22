package com.example.exceptions;

public class WrongException extends RuntimeException{
    public WrongException(String message) {
        super(message);
    }
}
