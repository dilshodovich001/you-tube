package com.example.exceptions;

public class RegionAlreadyExsistException extends RuntimeException{
    public RegionAlreadyExsistException(String message) {
        super(message);
    }
}
