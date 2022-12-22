package com.example.exceptions;

public class ArticleTypeAlreadyExitsException extends RuntimeException{
    public ArticleTypeAlreadyExitsException(String message) {
        super(message);
    }
}
