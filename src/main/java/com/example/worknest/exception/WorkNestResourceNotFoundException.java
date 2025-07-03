package com.example.worknest.exception;

public class WorkNestResourceNotFoundException extends RuntimeException{
    public WorkNestResourceNotFoundException(String message) {
        super(message);
    }
}
