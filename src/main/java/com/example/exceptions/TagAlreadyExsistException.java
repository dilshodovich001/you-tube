package com.example.exceptions;

public class TagAlreadyExsistException extends RuntimeException {
    public TagAlreadyExsistException(String message) {
        super(message);
    }
}
